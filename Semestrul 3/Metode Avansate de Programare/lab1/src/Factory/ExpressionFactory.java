package Factory;

import Enum.Operation;
import Model.*;

public class ExpressionFactory {
    private static ExpressionFactory instance;

    private ExpressionFactory() {}

    public static ExpressionFactory getInstance() {
        if(instance == null) {
            instance = new ExpressionFactory();
        }
        return instance;
    }

    public ComplexExpression createExpression(Operation operation, NumarComplex[] args) {
        switch(operation) {
            case ADDITION -> {
                return new Adaugare(args);
            }
            case SUBTRACT -> {
                return new Scadere(args);
            }
            case MULTIPLY -> {
                return new Inmultire(args);
            }
            case DIVIDE -> {
                return new Impartire(args);
            }
            default -> {
                return null;
            }
        }
    }
}
