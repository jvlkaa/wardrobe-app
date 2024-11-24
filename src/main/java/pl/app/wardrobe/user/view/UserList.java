package pl.app.wardrobe.user.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.app.wardrobe.factory.ModelFunctionFactory;
import pl.app.wardrobe.user.model.UsersModel;
import pl.app.wardrobe.user.service.UserService;

@RequestScoped
@Named
public class UserList {

    private final UserService userService;
    private UsersModel users;
    private final ModelFunctionFactory factory;

    @Inject
    public UserList(UserService userService, ModelFunctionFactory factory) {
        this.userService = userService;
        this.factory = factory;
    }

    public UsersModel getUsers() {
        if (users == null) {
            users = factory.usersToModel().apply(userService.findUserList());
        }
        return users;
    }

    public void  deleteAction(UsersModel.User user) {
        userService.delete(user.getId());
        users = null;
    }

}
