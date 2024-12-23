package map.socialnetwork.domain;

public class LoggedIn {
    private static String Nume;
    private static String Prenume;

    public static String getNume() {
        return Nume;
    }

    public static void setNume(String nume) {
        LoggedIn.Nume = nume;
    }

    public static String getPrenume() {
        return Prenume;
    }

    public static void setPrenume(String prenume) {
        LoggedIn.Prenume = prenume;
    }
}
