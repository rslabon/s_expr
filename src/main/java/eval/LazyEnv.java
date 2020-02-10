package eval;

import parser.Expression;

import java.util.HashMap;
import java.util.Map;

public class LazyEnv implements Env {
    private Env parent;
    private final Map<String, Object> mapping = new HashMap<>();

    public LazyEnv(Env parent) {
        this.parent = parent;
    }

    @Override
    public Env chain(Env context) {
        return new LazyEnv(this) {
            @Override
            public Object get(String name) {
                try {
                    return super.get(name);
                } catch (IllegalArgumentException e) {
                    return context.get(name);
                }
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return mapping.isEmpty();
    }

    @Override
    public Object get(String name) {
        Object value = mapping.get(name);
        if (value != null && value instanceof Expression) {
            Object materalizedValue = ((Expression) value).eval(this);
            mapping.put(name, materalizedValue);
        }
        Object actualValue = mapping.get(name);
        if (actualValue != null) {
            return actualValue;
        }
        if (parent != null) {
            return parent.get(name);
        }
        throw new IllegalArgumentException("Unbound variable: " + name);
    }

    @Override
    public void set(String varName, Expression value) {
        mapping.put(varName, value);
    }
}
