package pl.app.wardrobe.clothes.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.app.wardrobe.clothes.dto.GetClothesListResponse;
import pl.app.wardrobe.clothes.dto.GetClothesResponse;
import pl.app.wardrobe.clothes.dto.PatchClothesRequest;
import pl.app.wardrobe.clothes.dto.PutClothesRequest;

import java.util.UUID;

@Path("/clothes")
public interface ClothesController {

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putClothes(@PathParam("id") UUID id, PutClothesRequest request);

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    GetClothesListResponse getClothesList();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetClothesResponse getClothes(@PathParam("id") UUID id);

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchClothes(@PathParam("id") UUID id, PatchClothesRequest request);

    @DELETE
    @Path("/{id}")
    void deleteClothes(@PathParam("id") UUID id);
}
