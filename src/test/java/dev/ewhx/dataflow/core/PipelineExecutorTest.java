package dev.ewhx.dataflow.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PipelineExecutorTest {

    @Test
    void runReturnsSuccessResult() {
        Pipeline<String, Integer> pipeline = Pipeline.from(String.class)
                .addStage("parse", Integer::parseInt)
                .build();

        PipelineResult<Integer> result = PipelineExecutor.run(pipeline, "42");

        assertTrue(result.isSuccess());
        assertEquals(42, result.getOutput().orElseThrow());
        assertTrue(result.getError().isEmpty());
        assertNotNull(result.getStartTime());
        assertNotNull(result.getEndTime());
    }

    @Test
    void runReturnsFailureResult() {
        Stage<String, Integer> failing = input -> {
            throw new StageException("parse", "bad input");
        };
        Pipeline<String, Integer> pipeline = Pipeline.from(String.class)
                .addStage("parse", failing)
                .build();

        PipelineResult<Integer> result = PipelineExecutor.run(pipeline, "abc");

        assertFalse(result.isSuccess());
        assertTrue(result.getOutput().isEmpty());
        assertTrue(result.getError().isPresent());
    }
}
