package dev.ewhx.dataflow.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PipelineContextTest {

    @Test
    void putAndGetAttribute() {
        PipelineContext context = new PipelineContext();
        context.put("key", "value");

        assertEquals("value", context.get("key", String.class).orElseThrow());
    }

    @Test
    void getReturnsEmptyForMissingKey() {
        PipelineContext context = new PipelineContext();

        assertTrue(context.get("missing", String.class).isEmpty());
    }

    @Test
    void getReturnsEmptyForTypeMismatch() {
        PipelineContext context = new PipelineContext();
        context.put("key", 123);

        assertTrue(context.get("key", String.class).isEmpty());
    }

    @Test
    void containsReturnsTrueForExistingKey() {
        PipelineContext context = new PipelineContext();
        context.put("key", "value");

        assertTrue(context.contains("key"));
        assertFalse(context.contains("other"));
    }

    @Test
    void removeDeletesAttribute() {
        PipelineContext context = new PipelineContext();
        context.put("key", "value");
        context.remove("key");

        assertFalse(context.contains("key"));
    }

    @Test
    void clearRemovesAllAttributes() {
        PipelineContext context = new PipelineContext();
        context.put("a", 1);
        context.put("b", 2);
        context.clear();

        assertTrue(context.getAll().isEmpty());
    }
}
