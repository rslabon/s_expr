package core;

import core.specialform.SpecialForm;
import core.specialform.SpecialForms;
import eval.Env;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SExpr extends Expression {
    private List<Expression> expressions = new ArrayList<>();

    public SExpr(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public SExpr() {
        this(Collections.emptyList());
    }

    public SExpr(Expression expression) {
        this(Collections.singletonList(expression));
    }

    public SExpr(Expression... expressions) {
        this(Arrays.asList(expressions));
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public Expression eval(Env env) {

        if (!expressions.isEmpty()) {
            Expression first = expressions.get(0);
            if (first instanceof Var) {
                // (add 1 2)
                // add is function that should be called with arguments
                return applyFunctionByName((Var) first, env);
            }
            if (first instanceof SExpr) {
                // ((lambda (x) x) 2)
                // immediately applied function
                return applyAnonymousFunction((SExpr) first, env);
            }
        }

        return null;
    }

    @Override
    public Object getValue() {
        return toString();
    }

    private Expression applyFunctionByName(Var functionName, Env env) {
        String varName = functionName.getName();
        SpecialForm specialForm = SpecialForms.INSTANCE.get(varName.toLowerCase());
        List<Expression> argValues = expressions.subList(1, expressions.size());

        if (specialForm != null) {
            return specialForm.eval(env, argValues);
        } else {
            if (argValues.size() > 0) {
                Expression thisExpr = argValues.get(0);
                return thisExpr.apply(varName, env, argValues);
            }
            throw new IllegalArgumentException("Unknown reference: " + varName);
        }
    }

    private Expression applyAnonymousFunction(SExpr lambda, Env env) {
        List<Expression> lambdaParts = lambda.expressions;
        List<Expression> lambdaDefinition = lambdaParts.subList(1, lambdaParts.size());
        List<Expression> argValues = expressions.subList(1, expressions.size());
        Deferred deferred = Deferred.from(lambdaDefinition);

        return deferred.apply(null, env, argValues);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SExpr sExpr = (SExpr) o;

        return expressions != null ? expressions.equals(sExpr.expressions) : sExpr.expressions == null;
    }

    @Override
    public int hashCode() {
        return expressions != null ? expressions.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "(" +
                expressions
                        .stream()
                        .map(Object::toString)
                        .reduce(" ", (s1, s2) -> s1 + s2 + " ") +
                ")";
    }
}
