package dev.ewhx.dataflow.core;

@FunctionalInterface
public interface Stage<I, O> {

    O process(I input) throws StageException;

    default <R> Stage<I, R> andThen(Stage<O, R> next) {
        return input -> next.process(this.process(input));
    }
}
