package parser;

import eval.Env;
import eval.LazyEnv;

public class Deferred extends Expression {

    private final Env context;
    private final Expression body;

    public Deferred(Env context, Expression body) {
        this.context = context;
        this.body = body;
    }

    @Override
    public Object eval(Env env) {
        return body.eval(LazyEnv.merge(env, context));
    }
}
