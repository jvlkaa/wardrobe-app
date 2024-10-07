package pl.app.wardrobe.user.repository.memory;

import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.datasource.DataSource;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserInMemoryRepository implements UserRepository {

    private final DataSource dataSource;

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
    public Optional<User> findUserByLogin(String login) {
        return dataSource.findUserList().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return dataSource.findUserList().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<User> findUserList() {
        return dataSource.findUserList();
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

