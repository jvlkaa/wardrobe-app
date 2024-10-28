package pl.app.wardrobe.clothes.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.app.wardrobe.clothes.dto.GetItemListResponse;
import pl.app.wardrobe.clothes.dto.GetItemResponse;
import pl.app.wardrobe.clothes.dto.PatchItemRequest;
import pl.app.wardrobe.clothes.dto.PutItemRequest;

import java.io.InputStream;
import java.util.UUID;

@Path("/clothes/{clothesId}/items")
public interface ItemController {

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putItem(@PathParam("clothesId") UUID clothesId, @PathParam("id") UUID id, PutItemRequest request);

//    @GET
//    @Path("")
//    @Produces(MediaType.APPLICATION_JSON)
//    GetItemListResponse getItemList();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetItemResponse getItem(@PathParam("id") UUID id);

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    GetItemListResponse getItemListFromClothes(@PathParam("clothesId") UUID id);

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetItemListResponse getItemListFromUser(@PathParam("id") UUID id);

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchItem( @PathParam("id") UUID id, PatchItemRequest request);

    @DELETE
    @Path("/{id}")
    void deleteItem( @PathParam("id") UUID id);
}
