package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    @Override
    public MapSchema required() {
        super.required();
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck("sizeof", value -> value != null && value.size() == size);
        return this;
    }

    @SuppressWarnings("unchecked")
    public MapSchema shape(Map<?, ? extends BaseSchema<?>> schemas) {
        if (schemas == null) {
            return this;
        }

        addCheck(
                "shape",
                map -> {
                    for (Map.Entry<?, ? extends BaseSchema<?>> entry : schemas.entrySet()) {
                        Object key = entry.getKey();
                        BaseSchema schema = entry.getValue();
                        Object val = map.get(key);
                        try {
                            if (!schema.isValid(val)) {
                                return false;
                            }
                        } catch (ClassCastException e) {
                            return false;
                        }
                    }
                    return true;
                });
        return this;
    }
}
