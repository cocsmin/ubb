package Model;

public class NumarComplex {
    private double re;
    private double im;
    public NumarComplex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getReal() {
        return re;
    }

    public double getImag() {
        return im;
    }

    public NumarComplex adunare(NumarComplex numar) {
        return new NumarComplex(re + numar.re, im + numar.im);
    }

    public NumarComplex scadere(NumarComplex numar) {
        return new NumarComplex(re - numar.re, im - numar.im);
    }

    public NumarComplex inmultire(NumarComplex numar) {
        double temp_re = re * numar.re - im * numar.im;
        double temp_im = re * numar.im + im * numar.re;
        return new NumarComplex(temp_re, temp_im);
    }

    public NumarComplex impartire(NumarComplex numar) {
        double temp_re = (re * numar.re + im * numar.im) / (numar.re * numar.re + numar.im * numar.im);
        double temp_im = (numar.re * im - re * numar.im) / (numar.re * numar.re + numar.im * numar.im);
        return new NumarComplex(temp_re, temp_im);
    }

    @Override
    public String toString() {
        if(im>0)
            return re+ " + " + im + "*i";
        else if(this.im==0)
            return re + " ";
        else
            return re + " " + im + "*i";
    }
}
