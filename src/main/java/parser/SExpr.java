package parser;

import eval.Env;
import eval.LazyEnv;
import parser.specialform.SpecialForm;
import parser.specialform.SpecialForms;
import parser.std.StdFunction;
import parser.std.StdFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SExpr extends Expression {
    private List<Expression> expressions = new ArrayList<>();

    public SExpr() {
    }

    public SExpr(Expression expression) {
        expressions.add(expression);
    }

    public SExpr(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public SExpr(Expression... expressions) {
        this.expressions = Arrays.asList(expressions);
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public Object eval(Env env) {

        if (!expressions.isEmpty()) {
            Expression first = expressions.get(0);
            if (first instanceof Var) {

                String varName = ((Var) first).getName();
                SpecialForm specialForm = SpecialForms.INSTANCE.get(varName.toLowerCase());
                List<Expression> argValues = expressions.subList(1, expressions.size());

                if (specialForm != null) {
                    return specialForm.invoke(env, argValues);
                } else {
                    StdFunction stdFunction = StdFunctions.INSTANCE.get(varName);
                    if (stdFunction != null) {
                        return stdFunction.invoke(env, argValues);
                    }

                    Object function = env.get(varName);
                    if (function instanceof Deferred) {
                        return invokeDeferred(varName, (Deferred) function, argValues);
                    }
                    throw new IllegalArgumentException("Unknown reference: " + varName);
                }
            }
        }

        return null;
    }

    private Object invokeDeferred(String varName, Deferred deferred, List<Expression> argValues) {
        List<String> argNames = deferred.getArgs();

        if (argNames.size() != argValues.size()) {
            throw new IllegalArgumentException("Invalid number of arguments for: " + varName
                    + ". Actual: " + argValues.size() + " but expected: " + argNames.size());
        }

        LazyEnv deferredArgsEnv = new LazyEnv(null);
        for (int i = 0; i < argValues.size(); i++) {
            deferredArgsEnv.set(argNames.get(i), argValues.get(i));
        }

        return deferred.eval(deferredArgsEnv);
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
        return "parser.SExpr{" +
                "expressions=" + expressions +
                '}';
    }
}
