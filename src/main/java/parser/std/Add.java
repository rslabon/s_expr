package parser.std;

import eval.Env;
import parser.Expression;
import parser.LongNumber;

import java.util.Collections;
import java.util.List;

public class Add extends StdFunction {

    public static final String NAME = "+";

    public Add() {
        super(NAME, Collections.emptyList());
    }

    @Override
    public Object invoke(Env env, List<Expression> expressions) {
        return expressions.stream()
                .filter(expr -> expr instanceof LongNumber)
                .map(expr -> (LongNumber) expr)
                .map(expr -> (long) expr.eval())
                .reduce(0L, (a, b) -> a + b);
    }
}
