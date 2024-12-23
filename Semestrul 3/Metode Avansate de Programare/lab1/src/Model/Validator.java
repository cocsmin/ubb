package Model;

public class Validator {
    private String[] args;

    public Validator(String[] args) {
        this.args = args;
    }

    public boolean ValidateComplex(String nr) {

        if (nr.length() < 5)
            return false;

        if (nr.charAt(nr.length() - 1) != 'i')
            return false;

        if (nr.charAt(nr.length() - 2) != '*')
            return false;

        if (nr.charAt(0) != '-' && Character.isDigit(nr.charAt(0)) == false)
            return false;

        boolean trigger = false;
        double re = 0;
        double im = 0;

        for (int i = 0; i < nr.length() - 2; i++) {

            if (!(Character.isDigit(nr.charAt(i)))) {
                if (nr.charAt(i) != '-' && nr.charAt(i) != '+')
                    return false;
                trigger = true;
                continue;
            }
            if (trigger) {
                im *= 10;
                im += Character.getNumericValue(nr.charAt(i));
            } else {
                re *= 10;
                re += Character.getNumericValue(nr.charAt(i));
            }

        }

        return true;
    }

    public boolean ValidateWhole() {

        if (this.args.length < 2)
            return false;

        for (int i = 0; i < args.length; i += 2) {
            if (ValidateComplex(args[i]) == false)
                return false;
        }

        for (int i = 1; i < args.length; i += 2) {
            if (args[i].equals("*") == false && args[i].equals("+") == false && args[i].equals("-") == false && args[i].equals("/") == false)
                return false;
        }

        return true;

    }
}