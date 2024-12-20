package pl.app.wardrobe.clothes.controller.rest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pl.app.wardrobe.clothes.controller.api.ClothesController;
import pl.app.wardrobe.clothes.dto.GetClothesListResponse;
import pl.app.wardrobe.clothes.dto.GetClothesResponse;
import pl.app.wardrobe.clothes.dto.PatchClothesRequest;
import pl.app.wardrobe.clothes.dto.PutClothesRequest;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.factory.DtoFunctionFactory;
import jakarta.ws.rs.NotFoundException;
import pl.app.wardrobe.user.entity.Role;

import java.util.UUID;
import java.util.logging.Level;

@Path("/clothes")
@Log
public class ClothesRestController implements ClothesController {
    private ClothesService clothesService;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public ClothesRestController(DtoFunctionFactory dtoFunctionFactory,
                                 @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo){
        this.factory = dtoFunctionFactory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setClothesService(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @Override
    @RolesAllowed(Role.ADMIN)
    public void putClothes(UUID id, PutClothesRequest request) {
        try{
            clothesService.create(factory.requestToClothes().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ClothesController.class, "getClothes")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);

        } catch (EJBException ex) {
            if (ex.getCause() instanceof BadRequestException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }

    }

    @Override
    @RolesAllowed(Role.USER)
    public GetClothesListResponse getClothesList() {
        return factory.clothesListToResponse().apply(clothesService.findClothesList());
    }

    @Override
    @RolesAllowed(Role.USER)
    public GetClothesResponse getClothes(UUID id) {
        return clothesService.findClothesById(id)
                .map(factory.clothesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @RolesAllowed(Role.ADMIN)
    public void patchClothes(UUID id, PatchClothesRequest request) {
        clothesService.findClothesById(id).ifPresentOrElse(
                entity -> clothesService.update(factory.updateClothesWithRequest().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    @RolesAllowed(Role.ADMIN)
    public void deleteClothes(UUID id) {
        clothesService.findClothesById(id).ifPresentOrElse(
                entity -> clothesService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
