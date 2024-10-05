package pl.app.wardrobe.user.dto.function;

import pl.app.wardrobe.user.dto.GetUserResponse;
import pl.app.wardrobe.user.entity.User;

import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse> {

    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .build();
    }

}

