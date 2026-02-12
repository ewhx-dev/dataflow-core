package dev.ewhx.dataflow.core;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public final class PipelineResult<T> {

    private final T output;
    private final Throwable error;
    private final Instant startTime;
    private final Instant endTime;
    private final boolean success;

    private PipelineResult(T output, Throwable error, Instant startTime, Instant endTime, boolean success) {
        this.output = output;
        this.error = error;
        this.startTime = startTime;
        this.endTime = endTime;
        this.success = success;
    }

    public static <T> PipelineResult<T> success(T output, Instant startTime, Instant endTime) {
        return new PipelineResult<>(output, null, startTime, endTime, true);
    }

    public static <T> PipelineResult<T> failure(Throwable error, Instant startTime, Instant endTime) {
        return new PipelineResult<>(null, error, startTime, endTime, false);
    }

    public boolean isSuccess() {
        return success;
    }

    public Optional<T> getOutput() {
        return Optional.ofNullable(output);
    }

    public Optional<Throwable> getError() {
        return Optional.ofNullable(error);
    }

    public Duration getElapsed() {
        return Duration.between(startTime, endTime);
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }
}