package eval;

import org.junit.jupiter.api.Test;
import parser.Deferred;
import parser.LongNumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EvalTest {

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

        assertEquals(1L, actual.get("x"));
    }

    @Test
    public void reference() {
        LazyEnv env = new LazyEnv(null);
        env.set("y", new LongNumber(123));

        Env actual = new Eval().eval("(define x y)", env);

        assertEquals(123L, actual.get("x"));
    }

    @Test
    public void lambda() {
        LazyEnv env = new LazyEnv(null);

        Env actual = new Eval().eval("(define l (lambda(x) x))", env);

        Deferred value = (Deferred) actual.get("l");
        LazyEnv lambdaEnv = new LazyEnv(null);
        lambdaEnv.set("x", new LongNumber(999));
        assertEquals(999L, value.eval(lambdaEnv));
    }

    @Test
    public void lambdaWithContextReference() {
        LazyEnv env = new LazyEnv(null);
        env.set("y", new LongNumber(54));

        Env actual = new Eval().eval("(define l (lambda(x) y))", env);

        Deferred value = (Deferred) actual.get("l");
        LazyEnv lambdaEnv = new LazyEnv(null);
        assertEquals(54L, value.eval(lambdaEnv));
    }
}