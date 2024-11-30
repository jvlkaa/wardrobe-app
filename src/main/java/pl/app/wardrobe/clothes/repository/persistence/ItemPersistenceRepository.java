package pl.app.wardrobe.clothes.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.entity.Item_;
import pl.app.wardrobe.clothes.repository.api.ItemRepository;
import jakarta.persistence.NoResultException;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.clothes.entity.Clothes;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Dependent
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
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> root = query.from(Item.class);
        query.select(root);
        return manager.createQuery(query).getResultList();
    }

    @Override
    public Optional<Item> find(UUID primaryKey) {
        return Optional.ofNullable(manager.find(Item.class, primaryKey));
    }

    @Override
    public List<Item> findByCategory(UUID category) {
        return manager.find(Clothes.class, category).getItems();
    }

    @Override
    public List<Item> findByOwner(User owner) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> root = query.from(Item.class);
        query.select(root)
                .where(cb.equal(root.get(Item_.owner), owner));
        return manager.createQuery(query).getResultList();
    }

    @Override
    public Optional<Item> findByIdFromUser(User user, UUID id) {
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Item> query = cb.createQuery(Item.class);
            Root<Item> root = query.from(Item.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Item_.owner), user),
                            cb.equal(root.get(Item_.id), id)
                    ));
            return Optional.of(manager.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }

    @Override
    public List<Item> findByCategoryAndUser(User owner, Clothes clothesCategory) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> root = query.from(Item.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Item_.owner), owner),
                        cb.equal(root.get(Item_.clothesCategory), clothesCategory)
                ));
        return manager.createQuery(query).getResultList();
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
