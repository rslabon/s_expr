package parser;

import eval.Env;

public abstract class Expression {
    public abstract Object eval(Env env);

    public Object eval() {
        return eval(null);
    }
}

