package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.schemas.MapSchema;
import java.util.HashMap;
import java.util.Map;

import hexlet.code.schemas.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapSchemaTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new Validator();
    }

    @Test
    void testDefaultMapSchema() {
        MapSchema schema = validator.map();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isTrue();
    }

    @Test
    void testRequired() {
        MapSchema schema = validator.map().required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isTrue();
    }

    @Test
    void testSizeofWithoutRequired() {
        MapSchema schema = validator.map().sizeof(2);

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(new HashMap<>())).isFalse();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isFalse();

        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isTrue();
    }

    @Test
    void testSizeofWithRequired() {
        MapSchema schema = validator.map().required().sizeof(2);

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isFalse();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isFalse();

        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isTrue();

        data.put("key3", "value3");
        assertThat(schema.isValid(data)).isFalse();
    }

    @Test
    void testSizeofOverride() {
        MapSchema schema = validator.map().sizeof(2).sizeof(3);

        Map<String, String> data = new HashMap<>();
        data.put("k1", "v1");
        data.put("k2", "v2");
        assertThat(schema.isValid(data)).isFalse();

        data.put("k3", "v3");
        assertThat(schema.isValid(data)).isTrue();
    }
}
