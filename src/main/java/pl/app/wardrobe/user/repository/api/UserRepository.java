package pl.app.wardrobe.user.repository.api;

import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.repository.api.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository <User, UUID>{
    Optional<User> findUserByLogin(String login);
    Optional<User> findUserByEmail(String email);
    List<User> findUserList();
}
