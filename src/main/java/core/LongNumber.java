package core;

import eval.Env;

import java.util.List;

public class LongNumber extends Expression {

    private final long value;

    public LongNumber(long v) {
        value = v;
        functions.put("+", this::add);

        typeTransformers.put(LongNumber.class, () -> this);
        typeTransformers.put(DoubleNumber.class, () ->
                new DoubleNumber(getValue().doubleValue())
        );
        typeTransformers.put(Quote.class, () ->
                new Quote(getValue().toString())
        );
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
            LongNumber asLong = argValue.eval(env).cast(LongNumber.class);
            sum += asLong.getValue();
        }
        return new LongNumber(sum);
    }
}