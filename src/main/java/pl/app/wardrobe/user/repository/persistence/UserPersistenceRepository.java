package pl.app.wardrobe.user.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.entity.User_;
import pl.app.wardrobe.user.repository.api.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class UserPersistenceRepository implements UserRepository {

    private EntityManager manager;

    @PersistenceContext
    public void setManager(EntityManager manager) {
        this.manager = manager;
    }


    @Override
    public void create(User entity) {
        manager.persist(entity);
    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        return manager.createQuery(query).getResultList();
    }

    @Override
    public Optional<User> find(UUID primaryKey) {
        return Optional.ofNullable(manager.find(User.class, primaryKey));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(User_.login), login)
                    ));
            return Optional.of(manager.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(User_.email), email)
                    ));
            return Optional.of(manager.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void update(User entity) {
        manager.merge(entity);
    }

    @Override
    public void delete(User entity) {
        manager.remove(manager.find(User.class, entity.getId()));
    }
}

