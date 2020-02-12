package parser.std;

import eval.Env;
import parser.Expression;

import java.util.List;

public abstract class StdFunction {

    protected final String name;
    protected final List<String> args;

    public StdFunction(String name, List<String> args) {
        this.name = name;
        this.args = args;
    }

    public abstract Object invoke(Env env, List<Expression> argValues);
}
