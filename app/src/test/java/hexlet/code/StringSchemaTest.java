package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringSchemaTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new Validator();
    }

    @Test
    void testDefaultStringSchema() {
        StringSchema schema = validator.string();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid("what does the fox say")).isTrue();
    }

    @Test
    void testRequired() {
        StringSchema schema = validator.string().required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid("what does the fox say")).isTrue();
        assertThat(schema.isValid("hexlet")).isTrue();
    }

    @Test
    void testMinLengthWithoutRequired() {
        StringSchema schema = validator.string().minLength(5);

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid("hex")).isFalse();
        assertThat(schema.isValid("hello")).isTrue();
        assertThat(schema.isValid("hexlet")).isTrue();
    }

    @Test
    void testMinLengthWithRequired() {
        StringSchema schema = validator.string().required().minLength(5);

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid("hex")).isFalse();
        assertThat(schema.isValid("hexlet")).isTrue();
    }

    @Test
    void testMinLengthOverride() {
        StringSchema schema = validator.string();

        assertThat(schema.minLength(10).minLength(4).isValid("Hexlet")).isTrue();
        assertThat(schema.minLength(4).minLength(10).isValid("Hexlet")).isFalse();
    }

    @Test
    void testContainsWithoutRequired() {
        StringSchema schema = validator.string().contains("wh");

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid("what does the fox say")).isTrue();
        assertThat(schema.isValid("hello world")).isFalse();
    }

    @Test
    void testContainsOverride() {
        StringSchema schema = validator.string();

        assertThat(schema.contains("wh").isValid("what does the fox say")).isTrue();
        assertThat(schema.contains("what").isValid("what does the fox say")).isTrue();
        assertThat(schema.contains("whatthe").isValid("what does the fox say")).isFalse();
        assertThat(schema.isValid("what does the fox say")).isFalse();
    }

    @Test
    void testCombinedConstraints() {
        StringSchema schema = validator.string().required().minLength(5).contains("hex");

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid("hex")).isFalse();
        assertThat(schema.isValid("long-string")).isFalse();
        assertThat(schema.isValid("hexlet")).isTrue();
    }
}
