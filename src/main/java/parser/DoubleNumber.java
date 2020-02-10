package parser;

import eval.Env;

public class DoubleNumber extends Expression {
    private final double value;

    public DoubleNumber(double v) {
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
}
