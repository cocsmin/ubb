package Model;
import Model.*;
public class Teste {
    public void test_adunare(){
        NumarComplex nr1 = new NumarComplex(1.0,2.0);
        NumarComplex nr2 = new NumarComplex(3.0,3.0);
        NumarComplex rez = nr1.adunare(nr2);
        if ((rez.getReal() != 4.0) || (rez.getImag() != 5))
            System.out.println("TEST ADUNARE FAILED!");
    }

    public void test_inmultire(){
        NumarComplex nr1 = new NumarComplex(3.0,-2.0);
        NumarComplex nr2 = new NumarComplex(3.0,3.0);
        NumarComplex rez = nr1.inmultire(nr2);
        if ((rez.getReal() != 15.0) || (rez.getImag() != 3))
            System.out.println("TEST INMULTIRE FAILED!");
    }

    public void test_scadere(){
        NumarComplex nr1 = new NumarComplex(5.0,3.0);
        NumarComplex nr2 = new NumarComplex(3.0,2.0);
        NumarComplex rez = nr1.scadere(nr2);
        if ((rez.getReal() != 2.0) || (rez.getImag() != 1))
            System.out.println("TEST SCADERE FAILED!");
    }

    public void test_impartire(){
        NumarComplex nr1 = new NumarComplex(9.0,5.0);
        NumarComplex nr2 = new NumarComplex(4.0,2.0);
        NumarComplex rez = nr1.impartire(nr2);
        if ((rez.getReal() != 2.3) || (rez.getImag() != 0.1))
            System.out.println("TEST IMPARTIRE FAILED!");
    }

}
