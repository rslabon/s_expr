package parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class DefaultParser implements Parser {

    @Override
    public Expression parse(String text) {
        SExpressionTokenizer tokenizer = new SExpressionTokenizer(text);
        return parse(tokenizer.iterator()).get(0);
    }

    public List<Expression> parse(Iterator<String> tokens) {
        List<Expression> result = new ArrayList<>();
        while (tokens.hasNext()) {
            String token = tokens.next();
            if (token.equals("(")) {
                List<Expression> expressions = parse(tokens);
                result.add(new SExpr(expressions));
            } else if (token.equals(")")) {
                return result;
            } else {
                result.add(parseToken(token));
            }
        }
        return result;
    }

    static class SExpressionTokenizer implements Iterable<String> {
        private static final int NO_QUOTE = 0;
        private static final int QUOTE_END = 2;
        static final char QUOTE_CHAR = '\'';

        private final String text;

        public SExpressionTokenizer(String text) {
            this.text = text.trim();
        }

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                int index = 0;

                @Override
                public boolean hasNext() {
                    return text.substring(index).trim().length() > 0;
                }

                @Override
                public String next() {
                    String token = "";
                    int quote = NO_QUOTE;
                    while (index < text.length()) {
                        char c = text.charAt(index++);
                        if (token.length() == 0 && c == ' ') {
                            continue;//skip whitespaces at the beginning
                        }
                        if (c == '\n') {
                            continue;//skip new lines
                        }
                        if (c == QUOTE_CHAR) {
                            quote++;
                        }
                        if (quote == NO_QUOTE) {
                            if (c == '(' || c == ')' || c == ' ') {
                                if (token.length() == 0) {
                                    return String.valueOf(c);
                                } else /* "xxx(" or "xxx)" or "xxx " */ {
                                    index--;//backtrack one char leave only "xxx"
                                }
                                return token;
                            }
                        } else if (quote == QUOTE_END) {
                            token += String.valueOf(c);
                            return token;
                        }

                        token += String.valueOf(c);
                    }
                    return token;
                }
            };
        }

        @Override
        public void forEach(Consumer<? super String> action) {
        }

        @Override
        public Spliterator<String> spliterator() {
            return null;
        }
    }

    private static Expression parseToken(String token) {
        try {
            long number = Long.parseLong(token);
            return new LongNumber(number);
        } catch (Exception e) {
            try {
                double number = Double.parseDouble(token);
                return new DoubleNumber(number);
            } catch (Exception ex) {
                if (token.equalsIgnoreCase("true")) {
                    return new Bool(true);
                }
                if (token.equalsIgnoreCase("false")) {
                    return new Bool(false);
                }

                if (token.charAt(0) == SExpressionTokenizer.QUOTE_CHAR) {
                    token = token.substring(1);
                    if (token.charAt(token.length() - 1) == SExpressionTokenizer.QUOTE_CHAR) {
                        token = token.substring(0, token.length() - 1);
                    }
                    return new Quote(token);
                }

                return new Var(token);
            }
        }
    }
}
