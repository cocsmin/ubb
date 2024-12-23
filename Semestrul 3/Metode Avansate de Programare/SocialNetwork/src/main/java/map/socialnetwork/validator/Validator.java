package map.socialnetwork.validator;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
