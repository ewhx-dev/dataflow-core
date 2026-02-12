package dev.ewhx.dataflow.core;

import java.time.Instant;

public final class PipelineExecutor {

    private PipelineExecutor() {
    }

    public static <I, O> PipelineResult<O> run(Pipeline<I, O> pipeline, I input) {
        Instant start = Instant.now();
        try {
            O output = pipeline.execute(input);
            Instant end = Instant.now();
            return PipelineResult.success(output, start, end);
        } catch (StageException e) {
            Instant end = Instant.now();
            return PipelineResult.failure(e, start, end);
        }
    }
}
