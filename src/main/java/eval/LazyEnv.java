package eval;

import core.Expression;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LazyEnv implements Env {
    private final Map<String, Expression> mapping = new HashMap<>();

    public LazyEnv(LazyEnv parent) {
        if (parent != null) {
            this.mapping.putAll(parent.mapping);
        }
    }

    @Override
    public Env overrideWith(Env context) {
        LazyEnv chainEnv = new LazyEnv(null);
        chainEnv.mapping.putAll(this.mapping);
        chainEnv.mapping.putAll(((LazyEnv) context).mapping);
        return chainEnv;
    }

    @Override
    public boolean isEmpty() {
        return mapping.isEmpty();
    }

    @Override
    public Expression get(String name) {
        Expression value = mapping.get(name);
        if (value != null) {
            Expression materalizedValue = value.eval(this);
            mapping.put(name, materalizedValue);
            if (materalizedValue != null) {
                return materalizedValue;
            }
        }
        throw new IllegalArgumentException("Unbound variable: " + name);
    }

    @Override
    public boolean contains(String name) {
        return mapping.containsKey(name);
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
        return s;
    }
}
