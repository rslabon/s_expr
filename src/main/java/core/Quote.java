package core;

import eval.Env;

import java.util.List;

public class Quote extends Expression {
    private final String value;

    public Quote(String value) {
        this.value = value;
        this.functions.put("+", this::add);
    }

    @Override
    public Object eval(Env env) {
        return value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        return value != null ? value.equals(quote.value) : quote.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return value + " [q]";
    }

    private Object add(Env env, List<Expression> argValues) {
        String sum = "";
        for (Expression argValue : argValues) {
            Object evalArgValue = argValue.eval(env);
            if (evalArgValue instanceof Long) {
                sum += "" + (long) evalArgValue;
            }
            if (evalArgValue instanceof Double) {
                sum += "" + ((double) evalArgValue);
            }
            if (evalArgValue instanceof String) {
                sum += (String) evalArgValue;
            }
        }
        return sum;
    }
}
