package pl.app.wardrobe.user.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.app.wardrobe.datasource.component.DataSource;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class UserInMemoryRepository implements UserRepository {

    private final DataSource dataSource;

    @Inject
    public UserInMemoryRepository(DataSource source) {
        this.dataSource = source;
    }


    @Override
    public void create(User entity) {
        dataSource.createUser(entity);
    }

    @Override
    public List<User> findAll() {
        return dataSource.findUserList();
    }

    @Override
    public Optional<User> find(UUID primaryKey) {
        return dataSource.findUserList().stream()
                .filter(user -> user.getId().equals(primaryKey))
                .findFirst();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return dataSource.findUserList().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return dataSource.findUserList().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void update(User entity) {
        dataSource.updateUser(entity);
    }

    @Override
    public void delete(User entity) {
        dataSource.deleteUser(entity.getId());
    }
}

