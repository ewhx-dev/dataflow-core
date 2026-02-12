package dev.ewhx.dataflow.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConditionalStageTest {

    @Test
    void executesDelegateWhenConditionIsTrue() throws StageException {
        ConditionalStage<String, String> stage = ConditionalStage.of(
                s -> s.startsWith("A"),
                s -> s.toUpperCase(),
                s -> s.toLowerCase()
        );

        assertEquals("ALICE", stage.process("Alice"));
    }

    @Test
    void executesFallbackWhenConditionIsFalse() throws StageException {
        ConditionalStage<String, String> stage = ConditionalStage.of(
                s -> s.startsWith("A"),
                s -> s.toUpperCase(),
                s -> s.toLowerCase()
        );

        assertEquals("bob", stage.process("Bob"));
    }

    @Test
    void passThroughReturnsInputWhenConditionIsFalse() throws StageException {
        ConditionalStage<String, String> stage = ConditionalStage.passThrough(
                s -> s.length() > 5,
                s -> s.toUpperCase()
        );

        assertEquals("Hi", stage.process("Hi"));
    }
}
