package dev.ewhx.dataflow.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParallelPipelineTest {

    @Test
    void executesAllInputsAndCollectsResults() {
        Pipeline<Integer, Integer> pipeline = Pipeline.from(Integer.class)
                .addStage("double", n -> n * 2)
                .build();

        ParallelPipeline<Integer, Integer> parallel = ParallelPipeline.of(pipeline);

        List<PipelineResult<Integer>> results = parallel.executeAll(List.of(1, 2, 3));

        parallel.shutdown();

        assertEquals(3, results.size());
        assertTrue(results.stream().allMatch(PipelineResult::isSuccess));
        assertEquals(2, results.get(0).getOutput().orElseThrow());
        assertEquals(4, results.get(1).getOutput().orElseThrow());
        assertEquals(6, results.get(2).getOutput().orElseThrow());
    }
}
