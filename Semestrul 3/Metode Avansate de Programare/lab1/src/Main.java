import Factory.ExpressionFactory;
import Model.*;
import java.util.Scanner;
public class Main {
    public static void main(String args[]) {
        Teste teste = new Teste();
        teste.test_adunare();
        teste.test_inmultire();
        teste.test_scadere();
        teste.test_impartire();
        Validator val = new Validator(args);
        if (val.ValidateWhole()) {
            ExpressionParser parser = new ExpressionParser(args);
            ComplexExpression expression = parser.parse();
            NumarComplex rezultat = expression.execute();
            System.out.println("Rezultatul este: " + rezultat);
        }
        else {
            System.out.println("Expresia nu este valida!");
        }


    }
}