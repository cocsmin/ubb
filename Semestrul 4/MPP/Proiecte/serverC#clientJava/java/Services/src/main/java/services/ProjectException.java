package services;

public class ProjectException extends Exception {
    public ProjectException(String message) {
        super(message);
    }

    public ProjectException(){

    }

    public ProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
