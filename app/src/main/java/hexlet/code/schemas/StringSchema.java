package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    @Override
    public StringSchema required() {
        super.required();
        return this;
    }

    public StringSchema minLength(int min) {
        addCheck("minLength", value -> value != null && value.length() >= min);
        return this;
    }

    public StringSchema contains(String substring) {
        addCheck("contains", value -> value != null && value.contains(substring));
        return this;
    }

    @Override
    protected boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
