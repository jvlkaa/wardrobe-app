package pl.app.wardrobe.user.controller.api;

import pl.app.wardrobe.user.dto.GetUserResponse;
import pl.app.wardrobe.user.dto.GetUsersResponse;
import pl.app.wardrobe.user.dto.PatchUserRequest;
import pl.app.wardrobe.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.UUID;

public interface UserController {

    void putUser(UUID id, PutUserRequest request);
    GetUsersResponse getUserList();
    GetUserResponse getUser(UUID id);
    void patchUser(UUID id, PatchUserRequest request);
    void deleteUser(UUID id);


    /* avatars */
    void putUserAvatar(UUID id, InputStream photo);

    byte[] getUserAvatar(UUID id);

    void patchUserAvatar(UUID id, InputStream photo);


    void deleteUserAvatar(UUID id);
}
