package parser;

import eval.Env;

public class Quote extends Expression {
    private final String text;

    public Quote(String text) {
        this.text = text;
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
}
