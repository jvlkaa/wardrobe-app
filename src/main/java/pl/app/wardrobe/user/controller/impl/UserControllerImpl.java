package pl.app.wardrobe.user.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.controller.servlet.exception.BadRequestException;
import pl.app.wardrobe.controller.servlet.exception.NotFoundException;
import pl.app.wardrobe.controller.servlet.exception.ResourceConflictException;
import pl.app.wardrobe.dtofactory.DtoFunctionFactory;
import pl.app.wardrobe.user.controller.api.UserController;
import pl.app.wardrobe.user.dto.GetUserResponse;
import pl.app.wardrobe.user.dto.GetUsersResponse;
import pl.app.wardrobe.user.dto.PatchUserRequest;
import pl.app.wardrobe.user.dto.PutUserRequest;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final ItemService itemService;
    private final DtoFunctionFactory factory;

    @Inject
    public UserControllerImpl(UserService userService, ItemService itemService, DtoFunctionFactory factory){
        this.userService = userService;
        this.itemService = itemService;
        this.factory = factory;
    }

    @Override
    public void putUser(UUID id, PutUserRequest request) {
        try{
            userService.create(factory.requestToUser().apply(id, request));
        }
        catch (IllegalArgumentException e){
            throw new ResourceConflictException();
        }
    }

    @Override
    public GetUsersResponse getUserList() {
        return factory.usersToResponse().apply(userService.findUserList());
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return userService.findUserById(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void patchUser(UUID id, PatchUserRequest request) {
        userService.findUserById(id).ifPresentOrElse(
                entity -> userService.update(factory.updateUserWithRequest().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUser(UUID id) {
        userService.findUserById(id).ifPresentOrElse(
                entity -> {
                    itemService.findItemsByUser(id).ifPresentOrElse(
                            items -> items.forEach( item -> itemService.delete(item.getId())),
                            () -> {
                                throw new NotFoundException();
                            }
                    );
                    userService.delete(id);
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    /* avatars */
    @Override
    public void putUserAvatar(UUID id, InputStream photo) {
        userService.findUserById(id).ifPresentOrElse(
                entity -> {
                    try{
                        userService.putAvatar(id, photo);
                    } catch (NullPointerException ex) {
                        throw new BadRequestException();
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getUserAvatar(UUID id) {
        return userService.findUserById(id)
                .map(user -> userService.getAvatar(id))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void patchUserAvatar(UUID id, InputStream photo) {
        userService.findUserById(id).ifPresentOrElse(
                entity -> {
                    try {
                        userService.updateAvatar(id, photo);
                    } catch (IllegalStateException ex) {
                        throw new NotFoundException();
                    }
                } ,
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUserAvatar(UUID id) {
        userService.findUserById(id).ifPresentOrElse(
                entity -> userService.deleteAvatar(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
