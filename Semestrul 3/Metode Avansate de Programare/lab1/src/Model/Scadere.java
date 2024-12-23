package Model;

import static Enum.Operation.SUBTRACT;

public class Scadere extends ComplexExpression{
    public Scadere(NumarComplex[] args){
        super(SUBTRACT, args);
    }

    public NumarComplex executeOneOperation(NumarComplex n1, NumarComplex n2){
        return n1.scadere(n2);
    }
}