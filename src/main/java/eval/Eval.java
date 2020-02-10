package eval;

import parser.DefaultParser;
import parser.Expression;

public class Eval {

    private final DefaultParser parser = new DefaultParser();

    public Env eval(String code, Env env) {
        return eval(parser.parse(code), env);
    }

    public Env eval(Expression expression, Env env) {
        expression.eval(env);
        return env;
    }
}
