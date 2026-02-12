package dev.ewhx.dataflow.core;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RetryStageTest {

    @Test
    void succeedsOnFirstAttempt() throws StageException {
        RetryStage<String, String> stage = RetryStage.of(s -> s.toUpperCase(), 3);

        assertEquals("HELLO", stage.process("hello"));
    }

    @Test
    void retriesAndEventuallySucceeds() throws StageException {
        AtomicInteger attempts = new AtomicInteger(0);
        Stage<String, String> flaky = input -> {
            if (attempts.incrementAndGet() < 3) {
                throw new StageException("flaky", "temporary failure");
            }
            return input.toUpperCase();
        };

        RetryStage<String, String> stage = RetryStage.of(flaky, 3);

        assertEquals("HELLO", stage.process("hello"));
        assertEquals(3, attempts.get());
    }

    @Test
    void throwsAfterMaxAttempts() {
        Stage<String, String> failing = input -> {
            throw new StageException("fail", "always fails");
        };

        RetryStage<String, String> stage = RetryStage.of(failing, 2);

        StageException exception = assertThrows(StageException.class, () -> stage.process("input"));
        assertEquals("always fails", exception.getMessage());
    }
}
