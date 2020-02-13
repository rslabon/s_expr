package eval;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import core.Deferred;
import core.LongNumber;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EvalTest {

    //TODO collections
    //TODO special form: IF
    //TODO loop
    //TODO custom classes
    //TODO packages and imports
    //TODO iteropt with java

    @Test
    public void empty() {
        LazyEnv env = new LazyEnv(null);
        Env actual = new Eval().eval("()", env);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void define() {
        LazyEnv env = new LazyEnv(null);
        Env actual = new Eval().eval("(define x 1)", env);

        assertEquals(1L, actual.get("x").getValue());
    }

    @Test
    public void reference() {
        LazyEnv env = new LazyEnv(null);
        env.set("y", new LongNumber(123));

        Env actual = new Eval().eval("(define x y)", env);

        assertEquals(123L, actual.get("x").getValue());
    }

    @Test
    public void lambda() {
        LazyEnv env = new LazyEnv(null);

        Env actual = new Eval().eval("(define l (lambda(x) x))", env);

        Deferred value = (Deferred) actual.get("l");
        assertEquals(999L, value.apply(env, new LongNumber(999)).getValue());
    }

    @Test
    public void lambdaWithContextReference() {
        LazyEnv env = new LazyEnv(null);
        env.set("y", new LongNumber(54));

        Env actual = new Eval().eval("(define l (lambda() y))", env);

        Deferred value = (Deferred) actual.get("l");
        assertEquals(54L, value.apply(env).getValue());
    }

    @Test
    public void invokeLambda() {
        LazyEnv env = new LazyEnv(null);

        String code = "" +
                "(define self (lambda(x) x))\n" +
                "(define result (self 23))";

        Env actual = new Eval().eval(code, env);

        assertEquals(23L, actual.get("result").getValue());
    }

    @Test
    public void invokeLambdaNow() {
        LazyEnv env = new LazyEnv(null);

        String code = "(define result ((lambda(x) x) 23)";

        Env actual = new Eval().eval(code, env);

        assertEquals(23L, actual.get("result").getValue());
    }

    @Test
    public void lazyChain() {
        LazyEnv env = new LazyEnv(null);

        String code = ""+
                "(define inc (lambda(x) (+ x 1)))\n" +
                "(define result (inc (inc (inc 1))))";

        Env actual = new Eval().eval(code, env);

        assertEquals(4L, actual.get("result").getValue());
    }

    @Test
    public void scopes() {
        LazyEnv env = new LazyEnv(null);

        String code = ""+
                "(define x 2)\n" +
                "(define square (lambda(x) (+ x x)))\n" +
                "(define inc (lambda(x) (+ x 1)))\n" +
                "(define result (inc (square (square (square x)))))";

        Env actual = new Eval().eval(code, env);

        assertEquals(17L, actual.get("result").getValue());
    }

    @Test
    public void redefine() {
        LazyEnv env = new LazyEnv(null);

        String code = "" +
                "(define + (lambda(a b) b))\n" +
                "(define result (+ 1 2))";

        Env actual = new Eval().eval(code, env);

        assertEquals(2L, actual.get("result").getValue());
    }

    @Test
    public void invokeBuildinFunction() {
        LazyEnv env = new LazyEnv(null);

        String code = "(define result (+ 1 7))";

        Env actual = new Eval().eval(code, env);

        assertEquals(8L, actual.get("result").getValue());
    }

    @Test
    public void invokeBuildinFunctionNested() {
        LazyEnv env = new LazyEnv(null);

        String code = "(define result (+ 1 (+ 2 3)))";

        Env actual = new Eval().eval(code, env);

        assertEquals(6L, actual.get("result").getValue());
    }

    @Test
    public void invokeBuildinFunctionWithReference() {
        LazyEnv env = new LazyEnv(null);

        String code = "" +
                "(define a 3)\n" +
                "(define result (+ 1 a))";

        Env actual = new Eval().eval(code, env);

        assertEquals(4L, actual.get("result").getValue());
    }

    @Test
    public void invokeBuildinFunctionWithReference2() {
        LazyEnv env = new LazyEnv(null);

        String code = "" +
                "(define a 3)\n" +
                "(define result (+ a 1))";

        Env actual = new Eval().eval(code, env);

        assertEquals(4L, actual.get("result").getValue());
    }

    @Test
    public void manyExpressions() {
        LazyEnv env = new LazyEnv(null);

        String code = "" +
                "(define a 1)\n" +
                "(define b 2)";

        Env actual = new Eval().eval(code, env);

        assertEquals(1L, actual.get("a").getValue());
        assertEquals(2L, actual.get("b").getValue());
    }

    @Test
    public void manyExpressionsWithReference() {
        LazyEnv env = new LazyEnv(null);

        String code = "" +
                "(define a 7865)\n" +
                "(define b a)";

        Env actual = new Eval().eval(code, env);

        assertEquals(7865L, actual.get("a").getValue());
        assertEquals(7865L, actual.get("b").getValue());
    }

    @Test
    public void invalidReference() {
        LazyEnv env = new LazyEnv(null);

        String code = "(define a b)";

        Executable exec = () -> {
            Env actual = new Eval().eval(code, env);
            actual.get("a");
        };

        assertThrows(IllegalArgumentException.class, exec, "Unbound variable: b");
    }
}