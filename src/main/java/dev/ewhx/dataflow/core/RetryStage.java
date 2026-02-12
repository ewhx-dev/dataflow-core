package dev.ewhx.dataflow.core;

public final class RetryStage<I, O> implements Stage<I, O> {

    private final Stage<I, O> delegate;
    private final int maxAttempts;
    private final long delayMillis;

    private RetryStage(Stage<I, O> delegate, int maxAttempts, long delayMillis) {
        this.delegate = delegate;
        this.maxAttempts = maxAttempts;
        this.delayMillis = delayMillis;
    }

    public static <I, O> RetryStage<I, O> of(Stage<I, O> delegate, int maxAttempts) {
        return new RetryStage<>(delegate, maxAttempts, 0);
    }

    public static <I, O> RetryStage<I, O> of(Stage<I, O> delegate, int maxAttempts, long delayMillis) {
        return new RetryStage<>(delegate, maxAttempts, delayMillis);
    }

    @Override
    public O process(I input) throws StageException {
        StageException lastException = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return delegate.process(input);
            } catch (StageException e) {
                lastException = e;
                if (attempt < maxAttempts && delayMillis > 0) {
                    try {
                        Thread.sleep(delayMillis);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new StageException("retry", "Retry interrupted", ie);
                    }
                }
            }
        }
        throw lastException;
    }
}
