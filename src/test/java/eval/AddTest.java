package eval;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddTest {

    @Test
    public void long_simple() {
        LazyEnv env = new LazyEnv(null);

        String code = "(define result (+ 1 2))";

        Env actual = new Eval().eval(code, env);

        assertEquals(3L, actual.get("result").getValue());
    }

    @Test
    public void long_cast() {
        LazyEnv env = new LazyEnv(null);

        String code = "(define result (+ 1 2.0 '91'))";

        Env actual = new Eval().eval(code, env);

        assertEquals(94L, actual.get("result").getValue());
    }

    @Test
    public void string_simple() {
        LazyEnv env = new LazyEnv(null);

        String code = "(define result (+ 'a' 'b'))";

        Env actual = new Eval().eval(code, env);

        assertEquals("ab", actual.get("result").getValue());
    }

    @Test
    public void string_cast() {
        LazyEnv env = new LazyEnv(null);

        String code = "(define result (+ 'a' 1 2.5 'b'))";

        Env actual = new Eval().eval(code, env);

        assertEquals("a12.5b", actual.get("result").getValue());
    }

    @Test
    public void double_simple() {
        LazyEnv env = new LazyEnv(null);

        String code = "(define result (+ 1.1 2.5))";

        Env actual = new Eval().eval(code, env);

        assertEquals(3.6, actual.get("result").getValue());
    }

    @Test
    public void double_cast() {
        LazyEnv env = new LazyEnv(null);

        String code = "(define result (+ 1.1 2 '3.4'))";

        Env actual = new Eval().eval(code, env);

        assertEquals(6.5, actual.get("result").getValue());
    }
}