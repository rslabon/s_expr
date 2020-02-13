package core.specialform;

import core.Deferred;
import core.Expression;
import eval.Env;

import java.util.List;

public class Lambda extends SpecialForm {

    public static final String LAMBDA = "lambda";

    public Lambda() {
        super(LAMBDA, 2);
    }

    @Override
    public Expression doEval(Env env, List<Expression> parameters) {
        return Deferred.from(parameters);
    }

    @Override
    public String toString() {
        return "Lambda{" +
                "arguments=" + arguments +
                '}';
    }
}
