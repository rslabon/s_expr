package parser;

import eval.Env;

import java.util.List;

public class DoubleNumber extends Expression {
    private final double value;

    public DoubleNumber(double v) {
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
        return "parser.DoubleNumber{" +
                "value=" + value +
                '}';
    }

    private Object add(Env env, List<Expression> argValues) {
        double sum = 0.0;
        for (Expression argValue : argValues) {
            Object evalArgValue = argValue.eval(env);
            if (evalArgValue instanceof Long) {
                sum += ((Long) evalArgValue).doubleValue();
            }
            if (evalArgValue instanceof Double) {
                sum += ((double) evalArgValue);
            }
            if (evalArgValue instanceof String) {
                sum += Double.parseDouble((String) evalArgValue);
            }
        }
        return sum;
    }
}
