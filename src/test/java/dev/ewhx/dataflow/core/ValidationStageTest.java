package dev.ewhx.dataflow.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationStageTest {

    @Test
    void passesWhenAllRulesAreSatisfied() throws StageException {
        ValidationStage<String> stage = ValidationStage.<String>builder()
                .rule(s -> !s.isEmpty(), "must not be empty")
                .rule(s -> s.length() <= 10, "must not exceed 10 characters")
                .build();

        assertEquals("hello", stage.process("hello"));
    }

    @Test
    void throwsWhenRuleIsViolated() {
        ValidationStage<Integer> stage = ValidationStage.<Integer>builder()
                .rule(n -> n > 0, "must be positive")
                .build();

        StageException exception = assertThrows(StageException.class, () -> stage.process(-1));
        assertEquals("validation", exception.getStageName());
        assertTrue(exception.getMessage().contains("must be positive"));
    }

    @Test
    void reportsAllViolations() {
        ValidationStage<String> stage = ValidationStage.<String>builder()
                .rule(s -> s.length() >= 5, "too short")
                .rule(s -> s.matches("[a-z]+"), "must be lowercase letters")
                .build();

        StageException exception = assertThrows(StageException.class, () -> stage.process("AB"));
        assertTrue(exception.getMessage().contains("too short"));
        assertTrue(exception.getMessage().contains("must be lowercase letters"));
    }

    @Test
    void builderRequiresAtLeastOneRule() {
        assertThrows(IllegalStateException.class, () -> ValidationStage.<String>builder().build());
    }
}
