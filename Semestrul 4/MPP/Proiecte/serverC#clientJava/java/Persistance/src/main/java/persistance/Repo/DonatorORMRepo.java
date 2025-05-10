package persistance.Repo;
import model.Donator;
import org.hibernate.Session;
import persistance.DonatorRepo0;

import java.util.List;
import java.util.Optional;

public class DonatorORMRepo implements DonatorRepo0 {
    @Override
    public List<Donator> findByNameContaining(String partialName) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Donator WHERE nume_donator LIKE :partialName", Donator.class).list();
        }
    }

    @Override
    public Donator findByFullName(String numeDonator) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Donator WHERE nume_donator = :nume", Donator.class)
                    .setParameter("nume", numeDonator)
                    .uniqueResult();
        }
    }

    @Override
    public Donator save(Donator entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            session.persist(entity);
        });
        return entity;
    }

    @Override
    public Donator findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Donator.class, id);
        }
    }

    @Override
    public Iterable<Donator> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Donator", Donator.class).list();
        }

    }

    @Override
    public Donator update(Donator entity) {
        final Donator[] updatedDonator = new Donator[1];
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            updatedDonator[0] = session.merge(entity);
        });
        return updatedDonator[0];
    }

    @Override
    public Optional<Donator> findById(Integer id) {
        return null;
    }

    @Override
    public Donator deleteById(Integer integer) {
        return null;
    }

    @Override
    public Optional<Donator> existsById(Integer integer) {
        return Optional.empty();
    }
}
