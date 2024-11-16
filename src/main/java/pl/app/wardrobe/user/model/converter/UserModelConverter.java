package pl.app.wardrobe.user.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pl.app.wardrobe.factory.ModelFunctionFactory;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.model.UserModel;
import pl.app.wardrobe.user.service.UserService;
import java.util.Optional;

@FacesConverter(forClass = UserModel.class, managed = true)
public class UserModelConverter implements Converter<UserModel> {

    private final UserService userService;
    private final ModelFunctionFactory factory;

    @Inject
    public UserModelConverter(UserService userService, ModelFunctionFactory factory) {
        this.userService = userService;
        this.factory = factory;
    }

    @Override
    public UserModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<User> user = userService.findUserByLogin(value);
        return user.map(factory.userToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, UserModel value) {
        return value == null ? "" : value.getLogin();
    }
}

