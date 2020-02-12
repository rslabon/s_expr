package parser;

import eval.Env;
import eval.LazyEnv;
import parser.specialform.SpecialForm;
import parser.specialform.SpecialForms;

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
                if (specialForm != null) {
                    return specialForm.invoke(env, expressions.subList(1, expressions.size()));
                } else {
                    Object function = env.get(varName);
                    if (function instanceof Deferred) {
                        Deferred deferred = (Deferred) function;
                        List<String> argNames = deferred.getArgs();
                        List<Expression> argValues = this.expressions.subList(1, this.expressions.size());

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
                    throw new IllegalArgumentException("Unknown reference: " + varName);
                }
            }
        }

        return null;
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
