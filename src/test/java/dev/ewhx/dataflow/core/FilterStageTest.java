package dev.ewhx.dataflow.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterStageTest {

    @Test
    void filtersElementsMatchingPredicate() throws StageException {
        FilterStage<Integer> stage = FilterStage.of(n -> n > 3);

        List<Integer> result = stage.process(List.of(1, 2, 3, 4, 5));

        assertEquals(List.of(4, 5), result);
    }

    @Test
    void returnsEmptyListWhenNoMatch() throws StageException {
        FilterStage<String> stage = FilterStage.of(s -> s.startsWith("Z"));

        List<String> result = stage.process(List.of("Alpha", "Beta"));

        assertTrue(result.isEmpty());
    }
}
