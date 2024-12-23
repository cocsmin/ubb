package Model;

import java.util.Objects;

import Factory.ExpressionFactory;
import Enum.Operation;

public class ExpressionParser {
    private final String[] args;

    public ExpressionParser(String[] args) {
        this.args = args;
    }

    public ComplexExpression parse() {
        String op = args[1];
        NumarComplex[] argss = new NumarComplex[(args.length + 1) / 2];
        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0) {
                int neg = 0;
                String[] el = args[i].split("[+]", 2);
                if (el.length != 2) {
                    el = args[i].split("[-]", 2);
                    neg = 1;
                }
                double re = Double.parseDouble(el[0]);
                String[] IMAG = el[1].split("[*]", 2);
                double im = Double.parseDouble(IMAG[0]);
                if (neg == 1)
                    im = -im;

                NumarComplex numar = new NumarComplex(re, im);
                argss[i / 2] = numar;
            } else {
                op = args[i];
                if (!Objects.equals(op, "+") && !Objects.equals(op, "-") && !Objects.equals(op, "*") && !Objects.equals(op, "/")) {
                    return null;
                }
            }
        }
        return switch (op) {
            case "+" -> ExpressionFactory.getInstance().createExpression(Operation.ADDITION, argss);
            case "-" -> ExpressionFactory.getInstance().createExpression(Operation.SUBTRACT, argss);
            case "*" -> ExpressionFactory.getInstance().createExpression(Operation.MULTIPLY, argss);
            case "/" -> ExpressionFactory.getInstance().createExpression(Operation.DIVIDE, argss);
            default -> null;
        };
    }
}
