package eval;

import core.Expression;

public interface Env {
    boolean isEmpty();

    Object get(String name);

    boolean contains(String name);

    void set(String varName, Expression value);

    Env chain(Env context);
}
