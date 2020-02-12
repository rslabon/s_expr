package parser.specialform;

import eval.Env;
import parser.Deferred;
import parser.Expression;
import parser.SExpr;
import parser.Var;

import java.util.List;
import java.util.stream.Collectors;

public class Lambda extends SpecialForm {

    public static final String LAMBDA = "lambda";

    public Lambda() {
        super(LAMBDA, 2);
    }

    @Override
    public Object doInvoke(Env env, List<Expression> parameters) {
        Expression lambdaArgs = parameters.get(0);
        if (!(lambdaArgs instanceof SExpr)) {
            throw new IllegalArgumentException("Invalid lambda arguments! Proper use: (lambda (a b) (+ a b))");
        }

        List<String> args = ((SExpr) lambdaArgs).getExpressions()
                .stream()
                .filter(expr -> expr instanceof Var)
                .map(expr -> ((Var)expr))
                .map(Var::getName)
                .collect(Collectors.toList());

        Expression body = parameters.get(1);

        return new Deferred(env, args, body);
    }

    @Override
    public String toString() {
        return "Lambda{" +
                "arguments=" + arguments +
                '}';
    }
}
