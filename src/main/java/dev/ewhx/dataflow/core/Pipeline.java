package dev.ewhx.dataflow.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Pipeline<I, O> {

    private final List<StageEntry<?, ?>> stages;
    private final Stage<I, O> composedStage;

    private Pipeline(List<StageEntry<?, ?>> stages, Stage<I, O> composedStage) {
        this.stages = Collections.unmodifiableList(stages);
        this.composedStage = composedStage;
    }

    public static <T> Builder<T, T> from(Class<T> inputType) {
        return new Builder<>();
    }

    public O execute(I input) throws StageException {
        return composedStage.process(input);
    }

    public List<StageEntry<?, ?>> getStages() {
        return stages;
    }

    public int size() {
        return stages.size();
    }

    public static final class Builder<I, O> {

        private final List<StageEntry<?, ?>> stages = new ArrayList<>();
        private Stage<I, O> composed;

        @SuppressWarnings("unchecked")
        private Builder() {
            this.composed = (Stage<I, O>) (Stage<Object, Object>) input -> input;
        }

        @SuppressWarnings("unchecked")
        public <R> Builder<I, R> addStage(String name, Stage<O, R> stage) {
            stages.add(new StageEntry<>(name, stage));
            Stage<I, R> newComposed = input -> stage.process(composed.process(input));
            Builder<I, R> next = new Builder<>();
            next.stages.addAll(this.stages);
            next.composed = newComposed;
            return next;
        }

        public Pipeline<I, O> build() {
            if (stages.isEmpty()) {
                throw new IllegalStateException("Pipeline must contain at least one stage.");
            }
            return new Pipeline<>(stages, composed);
        }
    }
}
