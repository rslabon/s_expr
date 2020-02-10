package eval;

import parser.Expression;

import java.util.HashMap;
import java.util.Map;

public class LazyEnv implements Env {
    private final Env parentEnv;
    private final Map<String, Object> mapping = new HashMap<>();

    public LazyEnv(Env parentEnv) {
        this.parentEnv = parentEnv;
    }

    public boolean isEmpty() {
        return mapping.isEmpty();
    }

    public Object get(String name) {
        Object value = mapping.get(name);
        if (value != null && value instanceof Expression) {
            Object materalizedValue = ((Expression) value).eval(this);
            mapping.put(name, materalizedValue);
        }
        Object actualValue = mapping.get(name);
        return actualValue != null ? actualValue : parentEnv.get(name);
    }

    public void set(String varName, Expression value) {
        mapping.put(varName, value);
    }
}
