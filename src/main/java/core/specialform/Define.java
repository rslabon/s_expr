package core.specialform;

import core.Expression;
import core.Var;
import eval.Env;

import java.util.List;

public class Define extends SpecialForm {

    public static final String DEFINE = "define";

    public Define() {
        super(DEFINE, 2);
    }

    @Override
    public Expression doEval(Env env, List<Expression> parameters) {
        Expression first = parameters.get(0);
        if (first instanceof Var) {
            String varName = ((Var) first).getName();
            env.set(varName, parameters.get(1));
            return null;
        } else {
            throw new IllegalArgumentException("Invalid define: " + first);
        }
    }
}
