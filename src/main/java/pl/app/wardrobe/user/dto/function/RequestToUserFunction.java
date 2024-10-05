package pl.app.wardrobe.user.dto.function;

import pl.app.wardrobe.user.dto.PutUserRequest;
import pl.app.wardrobe.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {

    @Override
    public User apply(UUID id, PutUserRequest request) {
        return User.builder()
                .id(id)
                .login(request.getLogin())
                .password(request.getPassword())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .build();
    }

}

