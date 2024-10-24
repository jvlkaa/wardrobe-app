package pl.app.wardrobe.clothes.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.app.wardrobe.clothes.dto.GetItemListResponse;
import pl.app.wardrobe.clothes.dto.GetItemResponse;
import pl.app.wardrobe.clothes.dto.PatchItemRequest;
import pl.app.wardrobe.clothes.dto.PutItemRequest;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface ItemController {

    @PUT
    @Path("/item/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putItem(@PathParam("id") UUID id, PutItemRequest request);

    @GET
    @Path("/itemList")
    @Produces(MediaType.APPLICATION_JSON)
    GetItemListResponse getItemList();

    @GET
    @Path("/item/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetItemResponse getItem(@PathParam("id") UUID id);

    @GET
    @Path("/clothes/{id}/itemList")
    @Produces(MediaType.APPLICATION_JSON)
    GetItemListResponse getItemListFromClothes(@PathParam("id") UUID id);

    @GET
    @Path("/user/{id}/itemList/")
    @Produces(MediaType.APPLICATION_JSON)
    GetItemListResponse getItemListFromUser(@PathParam("id") UUID id);

    @PATCH
    @Path("/item/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchItem(@PathParam("id") UUID id, PatchItemRequest request);

    @DELETE
    @Path("/item/{id}")
    void deleteItem(@PathParam("id") UUID id);
}
