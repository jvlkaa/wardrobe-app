package pl.app.wardrobe.clothes.repository.persistence;

import jakarta.enterprise.context.Dependent;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.repository.api.ClothesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class ClothesPersistenceRepository implements ClothesRepository {
    private EntityManager manager;

    @PersistenceContext
    public void setManager(EntityManager manager) {
        this.manager = manager;
    }


    @Override
    public void create(Clothes entity) {
        manager.persist(entity);
    }

    @Override
    public List<Clothes> findAll() {
        return manager.createQuery("select c from Clothes c", Clothes.class).getResultList();
    }

    @Override
    public Optional<Clothes> find(UUID primaryKey) {
        return Optional.ofNullable(manager.find(Clothes.class, primaryKey));
    }

    @Override
    public void update(Clothes entity) {
        manager.merge(entity);
    }

    @Override
    public void delete(Clothes entity) {
        manager.remove(manager.find(Clothes.class, entity.getId()));
    }
}
