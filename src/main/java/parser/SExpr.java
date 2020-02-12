package parser;

import eval.Env;
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
                List<Expression> argValues = expressions.subList(1, expressions.size());

                if (specialForm != null) {
                    return specialForm.invoke(env, argValues);
                } else {
                    if (argValues.size() > 0) {
                        Expression thisExpr = argValues.get(0);
                        return thisExpr.invoke(varName, env, argValues);
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
