package pl.app.wardrobe.clothes.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.app.wardrobe.clothes.dto.GetClothesListResponse;
import pl.app.wardrobe.clothes.dto.GetClothesResponse;
import pl.app.wardrobe.clothes.dto.PatchClothesRequest;
import pl.app.wardrobe.clothes.dto.PutClothesRequest;

import java.util.UUID;

@Path("")
public interface ClothesController {

    @PUT
    @Path("/clothes/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putClothes(@PathParam("id") UUID id, PutClothesRequest request);

    @GET
    @Path("/clothesList")
    @Produces(MediaType.APPLICATION_JSON)
    GetClothesListResponse getClothesList();

    @GET
    @Path("/clothes/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetClothesResponse getClothes(@PathParam("id") UUID id);

    @PATCH
    @Path("/clothes/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchClothes(@PathParam("id") UUID id, PatchClothesRequest request);

    @DELETE
    @Path("/clothes/{id}")
    void deleteClothes(@PathParam("id") UUID id);
}
