package core;

import eval.Env;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Expression {

    public final Map<String, Function> functions = new HashMap<>();

    public Object eval() {
        return eval(null);
    }

    public abstract Object eval(Env env);

    public Object apply(String name, Env env, List<Expression> argValues) {
        if (env.contains(name)) {
            Object contextFunction = env.get(name);
            if (contextFunction instanceof Deferred) {
                return ((Deferred) contextFunction).apply(name, env, argValues);
            }
        }

        Function thisFunction = functions.get(name);
        if (thisFunction != null) {
            return thisFunction.apply(env, argValues);
        }

        if (argValues.size() > 0) {
            //reference case like (+ a 1) where a is reference to env
            Expression first = argValues.get(0);
            Object value = first.eval(env);
            if (value instanceof Expression) {
                return ((Expression) value).apply(name, env, argValues);
            }
        }

        throw new IllegalArgumentException("Function " + name + " is not defined in: " + getClass());
    }
}

