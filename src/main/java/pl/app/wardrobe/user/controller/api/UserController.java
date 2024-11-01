package pl.app.wardrobe.user.controller.api;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.app.wardrobe.user.dto.GetUserResponse;
import pl.app.wardrobe.user.dto.GetUsersResponse;
import pl.app.wardrobe.user.dto.PatchUserRequest;
import pl.app.wardrobe.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.UUID;

@Path("/users")
public interface UserController {

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putUser(@PathParam("id") UUID id, PutUserRequest request);

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUserList();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID id);

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchUser(@PathParam("id") UUID id, PatchUserRequest request);

    @DELETE
    @Path("/{id}")
    void deleteUser(@PathParam("id") UUID id);



    /* avatars */
    @PUT
    @Path("/{id}/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void putUserAvatar(@PathParam("id") UUID id, InputStream photo);

    @GET
    @Path("/{id}/avatar")
    @Produces("image/png")
    Response getUserAvatar(@PathParam("id") UUID id);

    @PATCH
    @Path("/{id}/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void patchUserAvatar(@PathParam("id") UUID id, InputStream photo);

    @DELETE
    @Path("/{id}/avatar")
    void deleteUserAvatar(@PathParam("id") UUID id);
}
