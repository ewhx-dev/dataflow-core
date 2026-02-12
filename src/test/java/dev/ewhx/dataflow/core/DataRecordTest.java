package dev.ewhx.dataflow.core;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DataRecordTest {

    @Test
    void buildAndRetrieveFields() {
        DataRecord record = DataRecord.builder()
                .field("name", "Alice")
                .field("age", 30)
                .build();

        assertEquals("Alice", record.get("name", String.class).orElseThrow());
        assertEquals(30, record.get("age", Integer.class).orElseThrow());
        assertEquals(2, record.size());
    }

    @Test
    void getReturnsEmptyForMissingField() {
        DataRecord record = DataRecord.builder()
                .field("key", "value")
                .build();

        assertTrue(record.get("missing", String.class).isEmpty());
    }

    @Test
    void hasReturnsTrueForExistingField() {
        DataRecord record = DataRecord.builder()
                .field("key", "value")
                .build();

        assertTrue(record.has("key"));
        assertFalse(record.has("other"));
    }

    @Test
    void mergesCombinesBothRecords() {
        DataRecord a = DataRecord.builder().field("x", 1).build();
        DataRecord b = DataRecord.builder().field("y", 2).build();

        DataRecord merged = a.merge(b);

        assertEquals(2, merged.size());
        assertEquals(1, merged.get("x", Integer.class).orElseThrow());
        assertEquals(2, merged.get("y", Integer.class).orElseThrow());
    }

    @Test
    void toMapReturnsUnmodifiableMap() {
        DataRecord record = DataRecord.builder()
                .field("key", "value")
                .build();

        Map<String, Object> map = record.toMap();

        assertThrows(UnsupportedOperationException.class, () -> map.put("new", "entry"));
    }
}
