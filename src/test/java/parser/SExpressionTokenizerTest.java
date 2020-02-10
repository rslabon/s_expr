package parser;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SExpressionTokenizerTest {

    @Test
    public void empty() {
        assertEqual("");
    }

    @Test
    public void simple() {
        assertEqual("()", "(", ")");
    }

    @Test
    public void nested() {
        assertEqual("(((((())))))", "(", "(", "(", "(", "(", "(", ")", ")", ")", ")", ")", ")");
    }

    @Test
    public void list() {
        assertEqual("(() () ())", "(", "(", ")", "(", ")", "(", ")", ")");
    }

    @Test
    public void code1() {
        assertEqual("(define my-name 2.89)", "(", "define", "my-name", "2.89", ")");
    }

    @Test
    public void code2() {
        assertEqual("(define (sum a b) (+ a b))", "(", "define", "(", "sum", "a", "b", ")", "(", "+", "a", "b", ")", ")");
    }

    @Test
    public void whitespaces() {
        assertEqual("(  define            my-name   2.89    )   ", "(", "define", "my-name", "2.89", ")");
    }

    @Test
    public void newlines() {
        assertEqual("\n(\n)\n", "(", ")");
        assertEqual("\n(\ndefine\n \nmy-name\n 2.89)\n", "(", "define", "my-name", "2.89", ")");
    }

    private String[] tokens(String code) {
        Iterator<String> iterator = new DefaultParser.SExpressionTokenizer(code).iterator();
        List<String> tokens = new ArrayList<>();
        while (iterator.hasNext()) {
            tokens.add(iterator.next());
        }
        return tokens.toArray(new String[]{});
    }

    public void assertEqual(String code, String... tokens) {
        String[] actual = tokens(code);
        assertArrayEquals(tokens, actual);
    }

}