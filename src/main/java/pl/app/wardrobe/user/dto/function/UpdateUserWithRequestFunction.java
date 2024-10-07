package pl.app.wardrobe.user.dto.function;

import pl.app.wardrobe.user.dto.PatchUserRequest;
import pl.app.wardrobe.user.entity.User;

import java.util.function.BiFunction;

public class UpdateUserWithRequestFunction implements BiFunction<User, PatchUserRequest, User> {

    @Override
    public User apply(User entity, PatchUserRequest request) {
        return User.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .avatar(entity.getAvatar())
                .build();
    }

}

