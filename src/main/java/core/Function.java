package core;

import eval.Env;

import java.util.List;

public interface Function {
    Expression apply(Env env, List<Expression> argValues);
} 