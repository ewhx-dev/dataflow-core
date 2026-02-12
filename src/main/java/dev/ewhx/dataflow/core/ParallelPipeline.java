package dev.ewhx.dataflow.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public final class ParallelPipeline<I, O> {

    private final Pipeline<I, O> pipeline;
    private final ExecutorService executor;

    private ParallelPipeline(Pipeline<I, O> pipeline, ExecutorService executor) {
        this.pipeline = pipeline;
        this.executor = executor;
    }

    public static <I, O> ParallelPipeline<I, O> of(Pipeline<I, O> pipeline) {
        return new ParallelPipeline<>(pipeline, Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()));
    }

    public static <I, O> ParallelPipeline<I, O> of(Pipeline<I, O> pipeline, ExecutorService executor) {
        return new ParallelPipeline<>(pipeline, executor);
    }

    public List<PipelineResult<O>> executeAll(List<I> inputs) {
        List<CompletableFuture<PipelineResult<O>>> futures = inputs.stream()
                .map(input -> CompletableFuture.supplyAsync(
                        () -> PipelineExecutor.run(pipeline, input), executor))
                .collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public void shutdown() {
        executor.shutdown();
    }
}
