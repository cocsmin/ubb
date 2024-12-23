package map.socialnetwork.repository;

import map.socialnetwork.domain.Entity;
import map.socialnetwork.validator.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepo<ID, E extends Entity<ID>> implements Repository<ID, E> {

    private Validator<E> validator;
    private Map<ID, E> entities;

    public InMemoryRepo(Validator<E> validator) {
        this.validator = validator;
        this.entities = new HashMap<ID, E>();
    }

    @Override
    public Optional<E> findOne(ID id) {
        if (id == null)
            throw new IllegalArgumentException("id nu poate fi null! ");
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public Optional<E> save(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("entitea nu poate fi null! ");
        validator.validate(entity);
        if (entities.get(entity.getId()) != null)
            return Optional.of(entity);
        else
            entities.put(entity.getId(), entity);

        return Optional.empty();
    }

    @Override
    public Optional<E> delete(ID id) {
        if (id == null)
            throw new IllegalArgumentException("id nu poate fi null! ");
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("entitea nu poate fi null! ");
        validator.validate(entity);
        entities.put(entity.getId(), entity);
        if (entities.get(entity.getId()) != null){
            entities.put(entity.getId(), entity);
            return Optional.empty();
        }
        return Optional.of(entity);
    }

}
