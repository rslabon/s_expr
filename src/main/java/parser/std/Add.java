package parser.std;

import eval.Env;
import parser.DoubleNumber;
import parser.Expression;
import parser.LongNumber;
import parser.Quote;

import java.util.Collections;
import java.util.List;

public class Add extends StdFunction {

    public static final String NAME = "+";

    public Add() {
        super(NAME, Collections.emptyList());
    }

    @Override
    public Object invoke(Env env, List<Expression> argValues) {
        Expression first = argValues.get(0);
        if (first instanceof LongNumber) {
            return addLongs(env, argValues);
        }
        if (first instanceof DoubleNumber) {
            return addDoubles(env, argValues);
        }
        if (first instanceof Quote) {
            return addString(env, argValues);
        }
        throw new IllegalArgumentException("Unsupported type: " + first.getClass());
    }

    private Object addLongs(Env env, List<Expression> argValues) {
        long sum = 0;
        for (Expression argValue : argValues) {
            Object evalArgValue = argValue.eval(env);
            if (evalArgValue instanceof Long) {
                sum += (long) evalArgValue;
            }
            if (evalArgValue instanceof Double) {
                sum += ((Double) evalArgValue).longValue();
            }
            if (evalArgValue instanceof String) {
                sum += Long.parseLong((String) evalArgValue);
            }
        }
        return sum;
    }

    private Object addDoubles(Env env, List<Expression> argValues) {
        double sum = 0.0;
        for (Expression argValue : argValues) {
            Object evalArgValue = argValue.eval(env);
            if (evalArgValue instanceof Long) {
                sum += ((Long) evalArgValue).doubleValue();
            }
            if (evalArgValue instanceof Double) {
                sum += ((double) evalArgValue);
            }
            if (evalArgValue instanceof String) {
                sum += Double.parseDouble((String) evalArgValue);
            }
        }
        return sum;
    }

    private Object addString(Env env, List<Expression> argValues) {
        String sum = "";
        for (Expression argValue : argValues) {
            Object evalArgValue = argValue.eval(env);
            if (evalArgValue instanceof Long) {
                sum += "" + (long) evalArgValue;
            }
            if (evalArgValue instanceof Double) {
                sum += "" + ((double) evalArgValue);
            }
            if (evalArgValue instanceof String) {
                sum += (String) evalArgValue;
            }
        }
        return sum;
    }
}
