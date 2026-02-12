package dev.ewhx.dataflow.core;

import java.time.Duration;
import java.time.Instant;

public final class StageMetrics {

    private final String stageName;
    private final Instant startTime;
    private final Instant endTime;
    private final boolean success;

    private StageMetrics(String stageName, Instant startTime, Instant endTime, boolean success) {
        this.stageName = stageName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.success = success;
    }

    public static <I, O> StageMetrics measure(String stageName, Stage<I, O> stage, I input) throws StageException {
        Instant start = Instant.now();
        try {
            stage.process(input);
            Instant end = Instant.now();
            return new StageMetrics(stageName, start, end, true);
        } catch (StageException e) {
            Instant end = Instant.now();
            throw e;
        }
    }

    public static StageMetrics ofSuccess(String stageName, Instant startTime, Instant endTime) {
        return new StageMetrics(stageName, startTime, endTime, true);
    }

    public static StageMetrics ofFailure(String stageName, Instant startTime, Instant endTime) {
        return new StageMetrics(stageName, startTime, endTime, false);
    }

    public String getStageName() {
        return stageName;
    }

    public Duration getElapsed() {
        return Duration.between(startTime, endTime);
    }

    public long getElapsedMillis() {
        return getElapsed().toMillis();
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public boolean isSuccess() {
        return success;
    }
}
