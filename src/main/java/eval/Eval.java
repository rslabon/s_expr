package eval;

import parser.DefaultParser;
import parser.Expression;
import parser.SExpr;
import parser.Var;

import java.util.List;

public class Eval {

    private final DefaultParser parser = new DefaultParser();

    public Env eval(String code, Env env) {
        return eval(parser.parse(code), env);
    }

    public Env eval(Expression expression, Env parentEnv) {
        LazyEnv env = new LazyEnv(parentEnv);

        if (expression instanceof SExpr) {
            List<Expression> expressions = ((SExpr) expression).getExpressions();
            if (!expressions.isEmpty()) {
                Expression first = expressions.get(0);
                if (first instanceof Var) {
                    String varName = ((Var) first).getName();
                    if (varName.equalsIgnoreCase("define")) {
                        applyDefine(env, expressions.get(1), expressions.get(2));
                    }
                }
            }
        }

        return env;
    }

    private void applyDefine(LazyEnv env, Expression name, Expression value) {
        if (name instanceof Var) {
            String varName = ((Var) name).getName();
            env.set(varName, value);
        }
    }
}
