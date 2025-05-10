package persistance.Repo;
import model.Caz;
import org.hibernate.Session;
import persistance.CazRepo0;

import java.util.Optional;

public class CazORMRepo implements CazRepo0 {

    @Override
    public Caz save(Caz entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            session.persist(entity);
        });
        return entity;
    }

    @Override
    public Caz findOne(Integer integer) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Caz.class, integer);
        }
    }

    @Override
    public Iterable<Caz> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Caz", Caz.class).list();
        }
    }

    @Override
    public Caz update(Caz entity) {
        final Caz[] updatedCaz = new Caz[1];
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            updatedCaz[0] = session.merge(entity);
        });
        return updatedCaz[0];
    }


    @Override
    public Optional<Caz> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Caz deleteById(Integer integer) {
        return null;
    }

    @Override
    public Optional<Caz> existsById(Integer integer) {
        return Optional.empty();
    }
}
