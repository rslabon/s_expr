package parser;

import java.util.List;

public class MutliExpressionParser {

    private final Parser parser;

    public MutliExpressionParser(Parser parser) {
        this.parser = parser;
    }

    public List<Expression> parse(String text) {
        Expression expr = parser.parse("(" + text + ")");
        if (expr instanceof SExpr) {
            return ((SExpr) expr).getExpressions();
        }
        throw new IllegalArgumentException("Code \"" + text + "\" is not valid lisp expression!");
    }
}
