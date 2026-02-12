package dev.ewhx.dataflow.core;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class FilterStage<T> implements Stage<List<T>, List<T>> {

    private final Predicate<T> predicate;

    private FilterStage(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    public static <T> FilterStage<T> of(Predicate<T> predicate) {
        return new FilterStage<>(predicate);
    }

    @Override
    public List<T> process(List<T> input) {
        return input.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
