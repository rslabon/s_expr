package parser;

import eval.Env;

public class LongNumber extends Expression {
    private final long value;

    public LongNumber(long v) {
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

        LongNumber that = (LongNumber) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }

    @Override
    public String toString() {
        return "parser.LongNumber{" +
                "value=" + value +
                '}';
    }
}
