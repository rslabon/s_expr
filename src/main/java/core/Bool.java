package core;

import eval.Env;

public class Bool extends Expression {
    private final boolean value;

    public Bool(boolean v) {
        value = v;
    }

    @Override
    public Object eval(Env env) {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bool bool = (Bool) o;

        return value == bool.value;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }

    @Override
    public String toString() {
        return value + " [b]";
    }
}