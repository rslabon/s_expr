package parser;

import eval.Env;

import java.util.List;

public interface Function {
    Object invoke(Env env, List<Expression> argValues);
} 