package core;

import eval.Env;

import java.util.List;

public class LongNumber extends Expression {

    private final long value;

    public LongNumber(long v) {
        value = v;
        functions.put("+", this::add);
    }

    @Override
    public Object eval(Env env) {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LongNumber that = (LongNumber) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }

    @Override
    public String toString() {
        return value + " [l]";
    }

    private Object add(Env env, List<Expression> argValues) {
        long sum = 0;
        for (Expression argValue : argValues) {
            Object evalArgValue = argValue.eval(env);
            if (evalArgValue instanceof Long) {
                sum += (long) evalArgValue;
            }
            if (evalArgValue instanceof Double) {
                sum += ((Double) evalArgValue).longValue();
            }
            if (evalArgValue instanceof String) {
                sum += Long.parseLong((String) evalArgValue);
            }
        }
        return sum;
    }
}