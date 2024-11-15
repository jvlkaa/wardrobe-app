package pl.app.wardrobe.user.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
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

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository userRepository;

    private final PasswordHash passwordHash;

    private final String avatarPath;

    @Inject
    public UserService(UserRepository userRepository, PasswordHash passwordHash){
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
        this.avatarPath = "../../../../../../src/avatars";
    }

    /* CRUD order */
    public void create(User user){
        user.setPassword(PasswordHash.hashPassword(user.getPassword().toCharArray()));
        if (userRepository.find(user.getId()).isPresent()) {
            throw new BadRequestException("User already exists.");
        }
        userRepository.create(user);
    }

    public List<User> findUserList(){
        return userRepository.findAll();
    }

    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
    public Optional<User> findUserByEmail(String email) {

        return userRepository.findByEmail(email);
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
                    String path = this.avatarPath + File.separator + avatar;
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
                String path = this.avatarPath + File.separator + id.toString() + ".png";
                if(user.getAvatar() == null){
                    throw new IllegalStateException();
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

    public void putAvatar(UUID id, InputStream iStream){
        userRepository.find(id).ifPresent(user ->{
            try {
                String path = this.avatarPath + File.separator + id.toString() + ".png";
                if(user.getAvatar() == null){
                    Files.copy(iStream, Paths.get(path));
                }
                else{
                    throw new NullPointerException();
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
            String path = this.avatarPath + File.separator + user.getAvatar();
            if (user.getAvatar() != null) {
                try {
                    Path filePath = Paths.get(path);
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                user.setAvatar(null);
                userRepository.update(user);
            }
            else{
                throw new NotFoundException();
            }
        });
    }

}
