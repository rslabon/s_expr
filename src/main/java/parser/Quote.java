package parser;

import eval.Env;

import java.util.List;

public class Quote extends Expression {
    private final String text;

    public Quote(String text) {
        this.text = text;
        this.functions.put("+", this::add);
    }

    @Override
    public Object eval(Env env) {
        return text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        return text != null ? text.equals(quote.text) : quote.text == null;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "parser.Quote{" +
                "text='" + text + '\'' +
                '}';
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
