package pl.app.wardrobe.repository.api;

import java.util.List;
import java.util.Optional;

public interface Repository <E, K> {
    /* CRUD operations */
    void create(E entity);
    List<E> findAll();
    Optional<E> find(K primaryKey);
    void update(E entity);
    void delete(E entity);
}
