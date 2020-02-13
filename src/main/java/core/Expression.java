package core;

import eval.Env;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Expression {

    final Map<String, Function> functions = new HashMap<>();
    final Map<Class<? extends Expression>, TypeTransformer> typeTransformers = new HashMap<>();

    public Expression eval() {
        return eval(null);
    }

    public abstract Expression eval(Env env);

    public abstract Object getValue();

    public Expression apply(String name, Env env, List<Expression> argValues) {
        if (env.contains(name)) {
            Expression contextFunction = env.get(name);
            if (contextFunction instanceof Deferred) {
                return contextFunction.apply(name, env, argValues);
            }
        }

        Function thisFunction = functions.get(name);
        if (thisFunction != null) {
            return thisFunction.apply(env, argValues);
        }

        if (argValues.size() > 0) {
            //reference case like (+ a 1) where a is reference to env
            Expression first = argValues.get(0);
            Expression value = first.eval(env);
            return value.apply(name, env, argValues);
        }

        throw new IllegalArgumentException("Function " + name + " is not defined in: " + getClass());
    }

    public Expression apply(Env env, Expression... argValues) {
        return apply(null, env, Arrays.asList(argValues));
    }

    public <T extends Expression> T cast(Class<? extends Expression> dest) {
        TypeTransformer typeTransformer = typeTransformers.get(dest);
        if (typeTransformer == null) {
            throw new IllegalArgumentException("Cannot cast: " + getClass() + " to " + dest);
        }
        return (T) typeTransformer.transform();
    }
}

interface TypeTransformer {
    Expression transform();
}

