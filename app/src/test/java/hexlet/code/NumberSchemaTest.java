package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberSchemaTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new Validator();
    }

    @Test
    void testDefaultNumberSchema() {
        NumberSchema schema = validator.number();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(5)).isTrue();
        assertThat(schema.isValid(-5)).isTrue();
        assertThat(schema.isValid(0)).isTrue();
    }

    @Test
    void testPositiveWithoutRequired() {
        NumberSchema schema = validator.number();

        assertThat(schema.positive().isValid(null)).isTrue();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(-10)).isFalse();
        assertThat(schema.isValid(0)).isFalse();
    }

    @Test
    void testRequired() {
        NumberSchema schema = validator.number().required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(-10)).isTrue();
        assertThat(schema.isValid(0)).isTrue();
    }

    @Test
    void testRequiredAndPositive() {
        NumberSchema schema = validator.number().positive().required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(-10)).isFalse();
        assertThat(schema.isValid(0)).isFalse();
    }

    @Test
    void testRange() {
        NumberSchema schema = validator.number();

        schema.range(5, 10);

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(5)).isTrue();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(4)).isFalse();
        assertThat(schema.isValid(11)).isFalse();
    }

    @Test
    void testRangeOverride() {
        NumberSchema schema = validator.number();

        schema.range(5, 10);
        schema.range(6, 9);

        assertThat(schema.isValid(5)).isFalse();
        assertThat(schema.isValid(10)).isFalse();
        assertThat(schema.isValid(9)).isTrue();
        assertThat(schema.isValid(6)).isTrue();
    }

    @Test
    void testCombinedConstraints() {
        NumberSchema schema = validator.number().required().positive().range(5, 10);

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(0)).isFalse();
        assertThat(schema.isValid(-5)).isFalse();
        assertThat(schema.isValid(4)).isFalse();
        assertThat(schema.isValid(5)).isTrue();
        assertThat(schema.isValid(7)).isTrue();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(11)).isFalse();
    }
}
