package eval;

import core.Expression;

public interface Env {
    boolean isEmpty();

    Expression get(String name);

    boolean contains(String name);

    void set(String varName, Expression value);

    Env overrideWith(Env context);
}
