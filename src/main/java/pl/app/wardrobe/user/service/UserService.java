package pl.app.wardrobe.user.service;

import pl.app.wardrobe.crypto.PasswordHash;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.repository.api.UserRepository;

import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;

    private final PasswordHash passwordHash;

    public UserService(UserRepository userRepository, PasswordHash passwordHash){
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    /* CRUD order */
    public void create(User user){
        user.setPassword(PasswordHash.hashPassword(user.getPassword().toCharArray()));
        userRepository.create(user);
    }

    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public Optional<User> findUserById(UUID id) {
        return userRepository.findUserById(id);
    }

    public boolean verify(String login, String password) {
        return findUserByLogin(login)
                .map(user -> PasswordHash.verifyPassword(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

}
