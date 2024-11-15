package pl.app.wardrobe.user.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pl.app.wardrobe.user.entity.User;
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
        return manager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public Optional<User> find(UUID primaryKey) {
        return Optional.ofNullable(manager.find(User.class, primaryKey));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            return Optional.of(manager.createQuery("select u from User u where u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return Optional.of(manager.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult());
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

