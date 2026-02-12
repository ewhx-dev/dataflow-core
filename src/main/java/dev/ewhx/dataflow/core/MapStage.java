package dev.ewhx.dataflow.core;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MapStage<I, O> implements Stage<List<I>, List<O>> {

    private final Function<I, O> mapper;

    private MapStage(Function<I, O> mapper) {
        this.mapper = mapper;
    }

    public static <I, O> MapStage<I, O> of(Function<I, O> mapper) {
        return new MapStage<>(mapper);
    }

    @Override
    public List<O> process(List<I> input) {
        return input.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
