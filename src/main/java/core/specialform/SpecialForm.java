package core.specialform;

import eval.Env;
import core.Expression;

import java.util.List;

public abstract class SpecialForm {
    final String name;
    final int arguments;

    public SpecialForm(String name, int arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public final Expression eval(Env env, List<Expression> parameters) {
        if (parameters.size() != arguments) {
            throw new IllegalArgumentException("Function: '" + name + "' error: invalid parameters size! Expected " + arguments + " but got " + parameters.size());
        }
        return doEval(env, parameters);
    }

    abstract Expression doEval(Env env, List<Expression> parameters);
}
