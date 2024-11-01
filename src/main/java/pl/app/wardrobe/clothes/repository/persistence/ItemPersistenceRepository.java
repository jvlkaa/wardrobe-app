package pl.app.wardrobe.clothes.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.repository.api.ItemRepository;
import jakarta.persistence.NoResultException;
import pl.app.wardrobe.user.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequestScoped
public class ItemPersistenceRepository implements ItemRepository {

    private EntityManager manager;

    @PersistenceContext
    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void create(Item entity) {
        manager.persist(entity);
    }

    @Override
    public List<Item> findAll() {
        return manager.createQuery("select i from Item i", Item.class).getResultList();
    }

    @Override
    public Optional<Item> find(UUID primaryKey) {
        return Optional.ofNullable(manager.find(Item.class, primaryKey));
    }

    @Override
    public List<Item> findByCategory(UUID category) {
        return manager.createQuery("select i from Item i where i.clothesCategory.id = :category", Item.class)
                .setParameter("category", category)
                .getResultList();

    }

    @Override
    public List<Item> findByOwner(User owner) {
        return manager.createQuery("select i from Item i where i.owner = :owner", Item.class)
                .setParameter("owner", owner)
                .getResultList();
    }

    @Override
    public Optional<Item> findByIdFromUser(User user, UUID id) {
        try {
            return Optional.of(manager.createQuery("select c from Item c where c.id = :id and c.owner = :user", Item.class)
                    .setParameter("user", user)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }

    @Override
    public void update(Item entity) {
        manager.merge(entity);
    }

    @Override
    public void delete(Item entity) {
        manager.remove(manager.find(Item.class, entity.getId()));
    }
}
