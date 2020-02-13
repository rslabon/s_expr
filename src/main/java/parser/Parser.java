package parser;

import core.Expression;

public interface Parser {
    Expression parse(String text);
}
