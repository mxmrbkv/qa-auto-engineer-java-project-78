package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.Validator;
import java.util.HashMap;
import java.util.Map;
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

    @Test
    void testShapeBasic() {
        MapSchema schema = validator.map();

        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        schema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertThat(schema.isValid(human1)).isTrue();

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertThat(schema.isValid(human2)).isFalse();

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertThat(schema.isValid(human3)).isFalse();

        // Without required on map itself, null map is valid
        assertThat(schema.isValid(null)).isTrue();
    }

    @Test
    void testShapeWithDifferentTypes() {
        MapSchema schema = validator.map().required();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", validator.string().required());
        schemas.put("age", validator.number().positive());

        schema.shape(schemas);

        Map<String, Object> person1 = new HashMap<>();
        person1.put("name", "Alice");
        person1.put("age", 25);
        assertThat(schema.isValid(person1)).isTrue();

        Map<String, Object> person2 = new HashMap<>();
        person2.put("name", "Bob");
        person2.put("age", -5);
        assertThat(schema.isValid(person2)).isFalse();

        Map<String, Object> person3 = new HashMap<>();
        person3.put("name", "Charlie");
        person3.put("age", null);
        assertThat(schema.isValid(person3)).isTrue();

        // Incompatible type in map
        Map<String, Object> person4 = new HashMap<>();
        person4.put("name", 12345);
        person4.put("age", 30);
        assertThat(schema.isValid(person4)).isFalse();

        // null map is invalid when map is required
        assertThat(schema.isValid(null)).isFalse();
    }

    @Test
    void testShapeCombinedWithSizeof() {
        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        MapSchema schema = validator.map().sizeof(2).shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertThat(schema.isValid(human1)).isTrue();

        human1.put("middleName", "Robert");
        assertThat(schema.isValid(human1)).isFalse();
    }

    @Test
    void testShapeOverride() {
        MapSchema schema = validator.map();

        Map<String, BaseSchema<String>> schemas1 = new HashMap<>();
        schemas1.put("city", validator.string().required().minLength(5));
        schema.shape(schemas1);

        Map<String, String> address = new HashMap<>();
        address.put("city", "Ufa");
        assertThat(schema.isValid(address)).isFalse();

        Map<String, BaseSchema<String>> schemas2 = new HashMap<>();
        schemas2.put("city", validator.string().required().minLength(2));
        schema.shape(schemas2);

        assertThat(schema.isValid(address)).isTrue();
    }
}
