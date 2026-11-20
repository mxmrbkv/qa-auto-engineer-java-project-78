package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected final Map<String, Predicate<T>> checks = new LinkedHashMap<>();
    protected boolean required = false;

    public BaseSchema<T> required() {
        this.required = true;
        return this;
    }

    protected boolean isEmpty(T value) {
        return value == null;
    }

    public boolean isValid(T value) {
        if (isEmpty(value)) {
            return !required;
        }

        for (Predicate<T> check : checks.values()) {
            if (!check.test(value)) {
                return false;
            }
        }
        return true;
    }

    protected void addCheck(String name, Predicate<T> check) {
        checks.put(name, check);
    }
}
