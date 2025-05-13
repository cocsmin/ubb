package persistance;

import model.Entity;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    T save(T entity);

    T findOne(ID id);

    Iterable<T> findAll();

    T update(T entity);

    Optional<T> findById(ID id);

    T deleteById(ID id);

    Optional<T> existsById(ID id);
}
