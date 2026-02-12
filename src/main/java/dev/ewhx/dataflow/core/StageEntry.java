package dev.ewhx.dataflow.core;

public final class StageEntry<I, O> {

    private final String name;
    private final Stage<I, O> stage;

    public StageEntry(String name, Stage<I, O> stage) {
        this.name = name;
        this.stage = stage;
    }

    public String getName() {
        return name;
    }

    public Stage<I, O> getStage() {
        return stage;
    }
}
