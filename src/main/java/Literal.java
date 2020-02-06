class Bool extends Expression {
    private final boolean value;

    public Bool(boolean v) {
        value = v;
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
        return "Bool{" +
                "value=" + value +
                '}';
    }
}

class LongNumber extends Expression {
    private final long value;

    public LongNumber(long v) {
        value = v;
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
        return "LongNumber{" +
                "value=" + value +
                '}';
    }
}

class DoubleNumber extends Expression {
    private final double value;

    public DoubleNumber(double v) {
        value = v;
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
        return "DoubleNumber{" +
                "value=" + value +
                '}';
    }
}