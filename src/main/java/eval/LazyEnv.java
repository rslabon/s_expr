package eval;

import core.Expression;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
                return super.contains(name) ? super.get(name) : context.get(name);
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
    public boolean contains(String name) {
        Object value = mapping.get(name);
        return value != null || parent != null && parent.contains(name);
    }

    @Override
    public void set(String varName, Expression value) {
        mapping.put(varName, value);
    }

    @Override
    public String toString() {
        String s = "*** " + super.toString() + " ***\n";
        s += mapping.entrySet()
                .stream()
                .map(e -> String.format("%-10s = %s", e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n"));
        s += "\n\n** Parent **\n";
        s += parent;
        return s;
    }
}
