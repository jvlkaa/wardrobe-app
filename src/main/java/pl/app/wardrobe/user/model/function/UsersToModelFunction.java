package pl.app.wardrobe.user.model.function;

import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.model.UsersModel;

import java.util.List;
import java.util.function.Function;

public class UsersToModelFunction implements Function<List<User>, UsersModel> {

    @Override
    public UsersModel apply(List<User> entity) {
        return UsersModel.builder()
                .users(entity.stream()
                        .map(character -> UsersModel.User.builder()
                                .id(character.getId())
                                .login(character.getLogin())
                                .build())
                        .toList())
                .build();
    }

}

