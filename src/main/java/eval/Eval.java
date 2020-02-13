package eval;

import parser.DefaultParser;
import core.Expression;
import parser.MutliExpressionParser;

import java.util.List;

public class Eval {

    private final MutliExpressionParser parser = new MutliExpressionParser(new DefaultParser());

    public Env eval(String code, Env env) {
        return eval(parser.parse(code), env);
    }

    private Env eval(List<Expression> expressions, Env env) {
        for (Expression expr : expressions) {
            expr.eval(env);
        }
        return env;
    }
}
