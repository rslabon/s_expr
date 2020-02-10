package eval;

import parser.Expression;

public interface Env {
    boolean isEmpty();

    Object get(String name);

    void set(String varName, Expression value);
}
