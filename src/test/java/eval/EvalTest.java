package eval;

import org.junit.jupiter.api.Test;
import parser.LongNumber;
import parser.SExpr;

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

//    @Test
//    public void function() {
//        LazyEnv env = new LazyEnv(null);
//        env.set("inc", new SExpr());
//
//        String code = "(define (inc x) (+ x 1))\n";
//
//        Env actual = new Eval().eval("(do (inc 1))", env);
//
//        assertEquals(123L, actual.get("x"));
//    }
}