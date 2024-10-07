package pl.app.wardrobe.user.dto.function;

import pl.app.wardrobe.user.dto.PatchPasswordRequest;
import pl.app.wardrobe.user.entity.User;

import java.util.function.BiFunction;

public class UpdateUserPasswordWithRequestFunction implements BiFunction<User, PatchPasswordRequest, User> {

    @Override
    public User apply(User entity, PatchPasswordRequest request) {
        return User.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .password(request.getPassword())
                .dateOfBirth(entity.getDateOfBirth())
                .email(entity.getEmail())
                .avatar(entity.getAvatar())
                .build();
    }

}

