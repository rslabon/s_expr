package parser;

import eval.Env;

public class Var extends Expression {
    private final String name;


    public Var(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Object eval(Env env) {
        return env.get(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Var var = (Var) o;

        return name != null ? name.equals(var.name) : var.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "parser.Var{" +
                "name='" + name + '\'' +
                '}';
    }
}
