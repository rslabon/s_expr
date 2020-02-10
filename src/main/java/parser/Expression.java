package parser;

import eval.Env;

public abstract class Expression {

    public Object eval() {
        return eval(null);
    }

    public abstract Object eval(Env env);
}

