package map.socialnetwork.repository;

import map.socialnetwork.domain.Entity;
import map.socialnetwork.repository.Page;
import map.socialnetwork.repository.Pageable;

import java.sql.SQLException;

public interface PagingRepo<ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAll(Pageable pageable) throws SQLException;
}