package dev.ewhx.dataflow.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public final class ValidationStage<T> implements Stage<T, T> {

    private final List<ValidationRule<T>> rules;

    private ValidationStage(List<ValidationRule<T>> rules) {
        this.rules = Collections.unmodifiableList(rules);
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    @Override
    public T process(T input) throws StageException {
        List<String> violations = new ArrayList<>();
        for (ValidationRule<T> rule : rules) {
            if (!rule.predicate().test(input)) {
                violations.add(rule.message());
            }
        }
        if (!violations.isEmpty()) {
            throw new StageException("validation", String.join("; ", violations));
        }
        return input;
    }

    public static final class Builder<T> {

        private final List<ValidationRule<T>> rules = new ArrayList<>();

        private Builder() {
        }

        public Builder<T> rule(Predicate<T> predicate, String message) {
            rules.add(new ValidationRule<>(predicate, message));
            return this;
        }

        public ValidationStage<T> build() {
            if (rules.isEmpty()) {
                throw new IllegalStateException("Validation stage must contain at least one rule.");
            }
            return new ValidationStage<>(rules);
        }
    }

    private record ValidationRule<T>(Predicate<T> predicate, String message) {
    }
}
