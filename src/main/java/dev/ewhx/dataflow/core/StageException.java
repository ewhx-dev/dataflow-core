package dev.ewhx.dataflow.core;

public class StageException extends Exception {

    private final String stageName;

    public StageException(String stageName, String message) {
        super(message);
        this.stageName = stageName;
    }

    public StageException(String stageName, String message, Throwable cause) {
        super(message, cause);
        this.stageName = stageName;
    }

    public String getStageName() {
        return stageName;
    }
}
