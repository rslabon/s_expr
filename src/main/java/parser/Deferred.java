package parser;

import eval.Env;

import java.util.List;

public class Deferred extends Expression {

    private final Env context;
    private final List<String> args;
    private final Expression body;

    public Deferred(Env context, List<String> args, Expression body) {
        this.context = context;
        this.args = args;
        this.body = body;
    }

    @Override
    public Object eval(Env env) {
        return body.eval(env.chain(context));
    }

    public List<String> getArgs() {
        return args;
    }
}
