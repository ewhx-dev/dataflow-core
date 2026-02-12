package dev.ewhx.dataflow.core;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class PipelineContext {

    private final Map<String, Object> attributes;

    public PipelineContext() {
        this.attributes = new ConcurrentHashMap<>();
    }

    public void put(String key, Object value) {
        attributes.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(String key, Class<T> type) {
        Object value = attributes.get(key);
        if (value != null && type.isInstance(value)) {
            return Optional.of((T) value);
        }
        return Optional.empty();
    }

    public boolean contains(String key) {
        return attributes.containsKey(key);
    }

    public void remove(String key) {
        attributes.remove(key);
    }

    public Map<String, Object> getAll() {
        return Collections.unmodifiableMap(attributes);
    }

    public void clear() {
        attributes.clear();
    }
}
