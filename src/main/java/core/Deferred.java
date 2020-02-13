package core;

import eval.Env;
import eval.LazyEnv;

import java.util.List;
import java.util.stream.Collectors;

public class Deferred extends Expression {

    private final List<String> argNames;
    private final Expression body;

    public Deferred(List<String> argNames, Expression body) {
        this.argNames = argNames;
        this.body = body;
    }

    public static Deferred from(List<Expression> lambdaDefinition) {
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

        return new Deferred(args, body);
    }

    @Override
    public Expression eval(Env env) {
        return this;
    }

    @Override
    public Object getValue() {
        return toString();
    }

    @Override
    public Expression apply(String name, Env env, List<Expression> argValues) {
        if (argNames.size() != argValues.size()) {
            String functionName = name == null ? "anonymous" : name;
            throw new IllegalArgumentException("Invalid number of arguments for: " + functionName
                    + ". Actual: " + argValues.size() + " but expected: " + argNames.size());
        }

        LazyEnv deferredArgsEnv = new LazyEnv(null);
        for (int i = 0; i < argValues.size(); i++) {
            deferredArgsEnv.set(argNames.get(i), argValues.get(i).eval(env));
        }

        return body.eval(env.overrideWith(deferredArgsEnv));
    }

    @Override
    public String toString() {
        return super.toString() + " argNames=" + argNames + " body=" + body;
    }
}
