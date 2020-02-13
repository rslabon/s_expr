package parser;

import core.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    @Test
    public void number() throws Exception {
        assertParse(new DoubleNumber(1.98), "1.98");
        assertParse(new LongNumber(1), "1");
        assertParse(new LongNumber(0), "0");
    }

    @Test
    public void bool() throws Exception {
        assertParse(new Bool(true), "true");
        assertParse(new Bool(false), "false");
    }

    @Test
    public void var() throws Exception {
        assertParse(new Var("var"), "var");
    }

    @Test
    public void quote() throws Exception {
        assertParse(new Quote("quoted text"), "'quoted text'");
        assertParse(new Quote("quoted"), "'quoted");
    }

    @Test
    public void s_expr() throws Exception {
        assertParse(new SExpr(), "()");
        assertParse(new SExpr(new SExpr()), "(())");
        assertParse(new SExpr(new SExpr(), new SExpr()), "(() ())");
        assertParse(new SExpr(new SExpr(), new SExpr(), new SExpr(new SExpr())), "(() () (()))");
        assertParse(new SExpr(new SExpr(new Var("v"), new DoubleNumber(1.5))), "((v 1.5))");
    }

    @Test
    public void s_expr_complex() throws Exception {
        SExpr expected = new SExpr(
                new Var("define"),
                new SExpr(new Var("sum"), new Var("a"), new Var("b")),
                new SExpr(new Var("+"), new Var("a"), new Var("b"))
        );
        assertParse(expected, "(define (sum a b) (+ a b))");
    }

    private void assertParse(Expression expected, String text) {
        assertEquals(expected, new DefaultParser().parse(text));
    }
}