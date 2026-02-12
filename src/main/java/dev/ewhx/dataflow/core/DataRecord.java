package dev.ewhx.dataflow.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class DataRecord {

    private final Map<String, Object> fields;

    private DataRecord(Map<String, Object> fields) {
        this.fields = Collections.unmodifiableMap(new LinkedHashMap<>(fields));
    }

    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(String key, Class<T> type) {
        Object value = fields.get(key);
        if (value != null && type.isInstance(value)) {
            return Optional.of((T) value);
        }
        return Optional.empty();
    }

    public boolean has(String key) {
        return fields.containsKey(key);
    }

    public Map<String, Object> toMap() {
        return fields;
    }

    public int size() {
        return fields.size();
    }

    public DataRecord merge(DataRecord other) {
        Map<String, Object> merged = new LinkedHashMap<>(this.fields);
        merged.putAll(other.fields);
        return new DataRecord(merged);
    }

    public static final class Builder {

        private final Map<String, Object> fields = new LinkedHashMap<>();

        private Builder() {
        }

        public Builder field(String key, Object value) {
            fields.put(key, value);
            return this;
        }

        public DataRecord build() {
            return new DataRecord(fields);
        }
    }
}
