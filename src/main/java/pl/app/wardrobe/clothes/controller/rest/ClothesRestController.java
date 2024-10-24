package pl.app.wardrobe.clothes.controller.rest;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pl.app.wardrobe.clothes.controller.api.ClothesController;
import pl.app.wardrobe.clothes.dto.GetClothesListResponse;
import pl.app.wardrobe.clothes.dto.GetClothesResponse;
import pl.app.wardrobe.clothes.dto.PatchClothesRequest;
import pl.app.wardrobe.clothes.dto.PutClothesRequest;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.factory.DtoFunctionFactory;
import jakarta.ws.rs.NotFoundException;
import java.util.UUID;

@Path("")
public class ClothesRestController implements ClothesController {
    private final ClothesService clothesService;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public ClothesRestController(ClothesService clothesService, DtoFunctionFactory dtoFunctionFactory,
                                 @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo){
        this.clothesService = clothesService;
        this.factory = dtoFunctionFactory;
        this.uriInfo = uriInfo;
    }

    @Override
    public void putClothes(UUID id, PutClothesRequest request) {
        try{
            clothesService.create(factory.requestToClothes().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ClothesController.class, "getClothes")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);

        }
        catch (IllegalArgumentException e){
            throw new BadRequestException(e);
        }
    }

    @Override
    public GetClothesListResponse getClothesList() {
        return factory.clothesListToResponse().apply(clothesService.findClothesList());
    }

    @Override
    public GetClothesResponse getClothes(UUID id) {
        return clothesService.findClothesById(id)
                .map(factory.clothesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void patchClothes(UUID id, PatchClothesRequest request) {
        clothesService.findClothesById(id).ifPresentOrElse(
                entity -> clothesService.update(factory.updateClothesWithRequest().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteClothes(UUID id) {
        clothesService.findClothesById(id).ifPresentOrElse(
                entity -> clothesService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
