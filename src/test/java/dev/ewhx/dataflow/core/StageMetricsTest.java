package dev.ewhx.dataflow.core;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class StageMetricsTest {

    @Test
    void successMetricsHaveCorrectState() {
        Instant start = Instant.now();
        Instant end = start.plusMillis(100);

        StageMetrics metrics = StageMetrics.ofSuccess("transform", start, end);

        assertEquals("transform", metrics.getStageName());
        assertTrue(metrics.isSuccess());
        assertEquals(100, metrics.getElapsedMillis());
    }

    @Test
    void failureMetricsHaveCorrectState() {
        Instant start = Instant.now();
        Instant end = start.plusMillis(50);

        StageMetrics metrics = StageMetrics.ofFailure("validate", start, end);

        assertEquals("validate", metrics.getStageName());
        assertFalse(metrics.isSuccess());
        assertEquals(50, metrics.getElapsedMillis());
    }
}
