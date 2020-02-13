package core;

import eval.Env;

import java.util.List;

public class DoubleNumber extends Expression {
    private final double value;

    public DoubleNumber(double v) {
        value = v;
        functions.put("+", this::add);
    }

    @Override
    public Double getValue() {
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

        DoubleNumber that = (DoubleNumber) o;

        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return value + " [d]";
    }

    private Expression add(Env env, List<Expression> argValues) {
        double sum = 0.0;
        for (Expression argValue : argValues) {
            Expression evalArgValue = argValue.eval(env);
            if (evalArgValue instanceof LongNumber) {
                sum += (((LongNumber) evalArgValue).getValue()).doubleValue();
            }
            if (evalArgValue instanceof DoubleNumber) {
                sum += (((DoubleNumber) evalArgValue).getValue());
            }
            if (evalArgValue instanceof Quote) {
                sum += Double.parseDouble(((Quote) evalArgValue).getValue());
            }
        }
        return new DoubleNumber(sum);
    }
}
