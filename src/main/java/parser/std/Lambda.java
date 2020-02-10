package parser.std;

import eval.Env;
import parser.Deferred;
import parser.Expression;
import parser.SExpr;

import java.util.List;

public class Lambda extends StdFunction {

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

        Expression body = parameters.get(1);

        return new Deferred(env, body);
    }

    @Override
    public String toString() {
        return "Lambda{" +
                "arguments=" + arguments +
                '}';
    }
}
