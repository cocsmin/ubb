package Model;

import static Enum.Operation.ADDITION;

public class Adaugare extends ComplexExpression{
    public Adaugare(NumarComplex[] args){
        super(ADDITION, args);
    }

    public NumarComplex executeOneOperation(NumarComplex n1, NumarComplex n2){
        return n1.adunare(n2);
    }
}
