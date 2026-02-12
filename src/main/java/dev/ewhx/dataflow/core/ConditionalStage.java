package dev.ewhx.dataflow.core;

import java.util.function.Predicate;

public final class ConditionalStage<I, O> implements Stage<I, O> {

    private final Predicate<I> condition;
    private final Stage<I, O> delegate;
    private final Stage<I, O> fallback;

    private ConditionalStage(Predicate<I> condition, Stage<I, O> delegate, Stage<I, O> fallback) {
        this.condition = condition;
        this.delegate = delegate;
        this.fallback = fallback;
    }

    public static <I, O> ConditionalStage<I, O> of(Predicate<I> condition, Stage<I, O> delegate, Stage<I, O> fallback) {
        return new ConditionalStage<>(condition, delegate, fallback);
    }

    public static <T> ConditionalStage<T, T> passThrough(Predicate<T> condition, Stage<T, T> delegate) {
        return new ConditionalStage<>(condition, delegate, input -> input);
    }

    @Override
    public O process(I input) throws StageException {
        if (condition.test(input)) {
            return delegate.process(input);
        }
        return fallback.process(input);
    }
}
