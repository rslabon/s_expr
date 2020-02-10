package parser.std;

import eval.Env;
import parser.Expression;
import parser.Var;

import java.util.List;

public class Define extends StdFunction {

    public static final String DEFINE = "define";

    public Define() {
        super(DEFINE, 2);
    }

    @Override
    public Object doInvoke(Env env, List<Expression> parameters) {
        Expression first = parameters.get(0);
        if (first instanceof Var) {
            String varName = ((Var) first).getName();
            env.set(varName, parameters.get(1));
        }
        return null;
    }
}
