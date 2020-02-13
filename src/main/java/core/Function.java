package core;

import eval.Env;

import java.util.List;

public interface Function {
    Object apply(Env env, List<Expression> argValues);
} 