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
    public Long getValue() {
        return value;
    }

    @Override
    public Expression eval(Env env) {
        return this;
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

    private Expression add(Env env, List<Expression> argValues) {
        long sum = 0;
        for (Expression argValue : argValues) {
            Expression evalArgValue = argValue.eval(env);
            if (evalArgValue instanceof LongNumber) {
                sum += ((LongNumber) evalArgValue).getValue();
            }
            if (evalArgValue instanceof DoubleNumber) {
                sum += ((DoubleNumber) evalArgValue).getValue().longValue();
            }
            if (evalArgValue instanceof Quote) {
                sum += Long.parseLong(((Quote) evalArgValue).getValue());
            }
        }
        return new LongNumber(sum);
    }
}