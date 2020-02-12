package parser;

import eval.Env;
import eval.LazyEnv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Expression {

    public final Map<String, Function> functions = new HashMap<>();

    public Object eval() {
        return eval(null);
    }

    public abstract Object eval(Env env);

    public Object invoke(String name, Env env, List<Expression> argValues) {
        Function thisFunction = functions.get(name);
        if (thisFunction != null) {
            return thisFunction.invoke(env, argValues);
        }

        Object contextFunction = env.get(name);
        if (contextFunction instanceof Deferred) {
            return invokeDeferred(name, (Deferred) contextFunction, argValues);
        }

        throw new IllegalArgumentException("Function " + name + " is not defined in: " + getClass());
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
}

