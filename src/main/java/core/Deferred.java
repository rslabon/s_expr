package core;

import eval.Env;
import eval.LazyEnv;

import java.util.List;
import java.util.stream.Collectors;

public class Deferred extends Expression {

    private final Env env;
    private final List<String> argNames;
    private final Expression body;

    public Deferred(Env env, List<String> argNames, Expression body) {
        this.env = env;
        this.argNames = argNames;
        this.body = body;
    }

    public static Deferred from(Env env, List<Expression> lambdaDefinition) {
        Expression lambdaArgs = lambdaDefinition.get(0);
        if (!(lambdaArgs instanceof SExpr)) {
            throw new IllegalArgumentException("Invalid lambda arguments! Proper use: (lambda (a b) (+ a b))");
        }

        List<String> args = ((SExpr) lambdaArgs).getExpressions()
                .stream()
                .filter(expr -> expr instanceof Var)
                .map(expr -> ((Var) expr))
                .map(Var::getName)
                .collect(Collectors.toList());

        Expression body = lambdaDefinition.get(1);

        return new Deferred(env, args, body);
    }

    @Override
    public Object eval(Env env) {
        return body.eval(env.chain(this.env));
    }

    @Override
    public Object apply(String name, Env env, List<Expression> argValues) {
        if (argNames.size() != argValues.size()) {
            String functionName = name == null ? "anonymous" : name;
            throw new IllegalArgumentException("Invalid number of arguments for: " + functionName
                    + ". Actual: " + argValues.size() + " but expected: " + argNames.size());
        }

        LazyEnv deferredArgsEnv = new LazyEnv(null);
        for (int i = 0; i < argValues.size(); i++) {
            deferredArgsEnv.set(argNames.get(i), argValues.get(i));
        }

        return eval(deferredArgsEnv);
    }

    @Override
    public String toString() {
        return super.toString() + " argNames=" + argNames + " body=" + body;
    }
}
