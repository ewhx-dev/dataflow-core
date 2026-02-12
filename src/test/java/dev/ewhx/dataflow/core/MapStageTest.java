package dev.ewhx.dataflow.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapStageTest {

    @Test
    void mapsEachElement() throws StageException {
        MapStage<String, Integer> stage = MapStage.of(String::length);

        List<Integer> result = stage.process(List.of("a", "bb", "ccc"));

        assertEquals(List.of(1, 2, 3), result);
    }

    @Test
    void returnsEmptyListForEmptyInput() throws StageException {
        MapStage<Integer, String> stage = MapStage.of(Object::toString);

        List<String> result = stage.process(List.of());

        assertTrue(result.isEmpty());
    }
}
