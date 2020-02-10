package parser;

import eval.Env;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Lambda extends Expression {
    private final List<String> arguments;
    private final SExpr body;

    public Lambda(SExpr body, List<String> arguments) {
        this.arguments = arguments;
        this.body = body;
    }

    public Lambda(SExpr body, String... arguments) {
        this.arguments = Arrays.asList(arguments);
        this.body = body;
    }

    public Lambda(SExpr body) {
        this(body, Collections.emptyList());
    }

    @Override
    public Object eval(Env env) {
        if (arguments.isEmpty()) {
            return body.eval(env);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lambda lambda = (Lambda) o;

        return arguments != null ? arguments.equals(lambda.arguments) : lambda.arguments == null;
    }

    @Override
    public int hashCode() {
        return arguments != null ? arguments.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Lambda{" +
                "arguments=" + arguments +
                '}';
    }
}
