package core.specialform;

import eval.Env;
import core.Expression;
import core.Var;

import java.util.List;

public class Define extends SpecialForm {

    public static final String DEFINE = "define";

    public Define() {
        super(DEFINE, 2);
    }

    @Override
    public Object doApply(Env env, List<Expression> parameters) {
        Expression first = parameters.get(0);
        if (first instanceof Var) {
            String varName = ((Var) first).getName();
            env.set(varName, parameters.get(1));
        }
        return null;
    }
}
