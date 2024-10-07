package pl.app.wardrobe.user.controller.api;

import pl.app.wardrobe.clothes.dto.PatchItemRequest;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.dtofactory.DtoFunctionFactory;
import pl.app.wardrobe.user.dto.GetUserResponse;
import pl.app.wardrobe.user.dto.GetUsersResponse;
import pl.app.wardrobe.user.dto.PatchUserRequest;
import pl.app.wardrobe.user.dto.PutUserRequest;
import pl.app.wardrobe.user.service.UserService;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface UserController {

    void putUser(UUID id, PutUserRequest request);
    GetUsersResponse getUserList();
    GetUserResponse getUser(UUID id);
    void patchUser(UUID id, PatchUserRequest request);
    void deleteUser(UUID id);


    /* avatars */

    byte[] getUserAvatar(UUID id);

    void patchUserAvatar(UUID id, InputStream photo);

    void deleteUserAvatar(UUID id);
}
