package pl.app.wardrobe.user.controller.impl;

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

public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final DtoFunctionFactory factory;

    public UserControllerImpl(UserService userService, DtoFunctionFactory factory){
        this.userService = userService;
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
                entity -> userService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    /* avatars */
    @Override
    public byte[] getUserAvatar(UUID id) {
        return userService.findUserById(id)
                .map(user -> userService.getAvatar(id))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void patchUserAvatar(UUID id, InputStream photo) {
        userService.findUserById(id).ifPresentOrElse(
                entity -> userService.updateAvatar(id, photo),
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
