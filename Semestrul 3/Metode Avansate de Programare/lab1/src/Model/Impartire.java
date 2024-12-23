package Model;

import static Enum.Operation.DIVIDE;

public class Impartire extends ComplexExpression{
    public Impartire(NumarComplex[] args){
        super(DIVIDE, args);
    }

    public NumarComplex executeOneOperation(NumarComplex n1, NumarComplex n2){
        return n1.impartire(n2);
    }
}