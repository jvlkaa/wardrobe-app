package pl.app.wardrobe.user.service;

import pl.app.wardrobe.controller.servlet.exception.NotFoundException;
import pl.app.wardrobe.crypto.PasswordHash;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.repository.api.UserRepository;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;

    private final PasswordHash passwordHash;

    private final String avatarPath;

    public UserService(UserRepository userRepository, PasswordHash passwordHash, String path){
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
        this.avatarPath = path;
    }

    /* CRUD order */
    public void create(User user){
        user.setPassword(PasswordHash.hashPassword(user.getPassword().toCharArray()));
        userRepository.create(user);
    }

    public List<User> findUserList(){
        return userRepository.findUserList();
    }

    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }
    public Optional<User> findUserByEmail(String email) {

        return userRepository.findUserByEmail(email);
    }

    public Optional<User> findUserById(UUID id) {
        return userRepository.find(id);
    }

    public boolean verify(String login, String password) {
        return findUserByLogin(login)
                .map(user -> PasswordHash.verifyPassword(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

    public void update(User user){
        userRepository.update(user);
    }

    public void delete(UUID id){
        userRepository.delete(userRepository.find(id).orElseThrow());
    }

    /* avatars */
    public byte[] getAvatar(UUID id) {
        return userRepository.find(id).map(user -> {
            try {
                String avatar = user.getAvatar();
                if (avatar != null) {
                    String path = this.avatarPath + "\\" + avatar;
                    return Files.readAllBytes(Paths.get(path));
                } else {
                    throw new NotFoundException("Avatar is not set");
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }).orElseThrow(() -> new NotFoundException("User does not exist"));
    }

    public void updateAvatar(UUID id, InputStream iStream){
        userRepository.find(id).ifPresent(user ->{
            try {
                String path = this.avatarPath + "\\" + id.toString() + ".png";
                if(user.getAvatar() == null){
                    Files.copy(iStream, Paths.get(path));
                }
                else{
                    Files.copy(iStream, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
                }
                user.setAvatar(id.toString() + ".png");
                userRepository.update(user);
            }
            catch (IOException e){
                throw new IllegalStateException(e);
            }
        });
    }

    public void deleteAvatar(UUID id){
        userRepository.find(id).ifPresent(user ->{
            String path = this.avatarPath + "\\" + user.getAvatar();
            if (path != null) {
                try {
                    Path filePath = Paths.get(path);
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                user.setAvatar(null);
                userRepository.update(user);
            }
        });
    }

}
