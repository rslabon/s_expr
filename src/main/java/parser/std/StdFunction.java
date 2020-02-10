package parser.std;

import eval.Env;
import parser.Expression;

import java.util.List;

public abstract class StdFunction {
    final String name;
    final int arguments;

    public StdFunction(String name, int arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public final Object invoke(Env env, List<Expression> parameters) {
        if (parameters.size() != arguments) {
            throw new IllegalArgumentException("Function: '" + name + "' error: invalid parameters size! Expected " + arguments + " but got " + parameters.size());
        }
        return doInvoke(env, parameters);
    }

    abstract Object doInvoke(Env env, List<Expression> parameters);
}
