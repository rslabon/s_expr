package core;

import eval.Env;

import java.util.List;

public class DoubleNumber extends Expression {
    private final double value;

    public DoubleNumber(double v) {
        value = v;
        functions.put("+", this::add);

        typeTransformers.put(DoubleNumber.class, () -> this);
        typeTransformers.put(LongNumber.class, () ->
                new LongNumber(getValue().longValue())
        );
        typeTransformers.put(Quote.class, () ->
                new Quote(getValue().toString())
        );
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
            DoubleNumber asDouble = argValue.eval(env).cast(DoubleNumber.class);
            sum += asDouble.getValue();
        }
        return new DoubleNumber(sum);
    }
}
