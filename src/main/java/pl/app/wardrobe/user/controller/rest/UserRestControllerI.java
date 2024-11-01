package pl.app.wardrobe.user.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.DtoFunctionFactory;
import pl.app.wardrobe.user.controller.api.UserController;
import pl.app.wardrobe.user.dto.GetUserResponse;
import pl.app.wardrobe.user.dto.GetUsersResponse;
import pl.app.wardrobe.user.dto.PatchUserRequest;
import pl.app.wardrobe.user.dto.PutUserRequest;
import pl.app.wardrobe.user.service.UserService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;

@Path("/users")
@Log
public class UserRestControllerI implements UserController {
    private final UserService userService;
    private final ItemService itemService;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public UserRestControllerI(UserService userService, ItemService itemService, DtoFunctionFactory factory,
                               @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo){
        this.userService = userService;
        this.itemService = itemService;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public void putUser(UUID id, PutUserRequest request) {
        try{
            userService.create(factory.requestToUser().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(UserController.class, "getUser")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);

        }
        catch (BadRequestException e) {
            log.log(Level.WARNING, e.getMessage(), e);
            throw e;
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

    public Response getUserAvatar(UUID id) {
        byte[] avatarBytes = userService.findUserById(id)
                .map(user -> userService.getAvatar(id))
                .orElseThrow(NotFoundException::new);

        return Response.ok(new ByteArrayInputStream(avatarBytes))
                .type("image/png")
                .build();
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
