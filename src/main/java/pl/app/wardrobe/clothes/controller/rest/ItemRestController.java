package pl.app.wardrobe.clothes.controller.rest;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pl.app.wardrobe.clothes.controller.api.ItemController;
import pl.app.wardrobe.clothes.dto.GetItemListResponse;
import pl.app.wardrobe.clothes.dto.GetItemResponse;
import pl.app.wardrobe.clothes.dto.PatchItemRequest;
import pl.app.wardrobe.clothes.dto.PutItemRequest;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.DtoFunctionFactory;
import java.util.logging.Level;
import java.util.List;
import java.util.UUID;
import jakarta.ejb.EJBException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@Path("/clothes/{clothesId}/items")
@Log
public class ItemRestController implements ItemController {
    private ItemService itemService;
    private ClothesService clothesService;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public ItemRestController(DtoFunctionFactory factory,
                              @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo){
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @EJB
    public void setClothesService(ClothesService clothesService) {
        this.clothesService = clothesService;
    }


    @Override
    public void putItem(UUID clothesId, UUID id, PutItemRequest request){
        try{
            itemService.create(factory.requestToItem().apply(clothesId, id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ItemController.class, "getItem")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);

        }
        catch (EJBException  ex) {
            if (ex.getCause() instanceof BadRequestException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;

        }
    }

//    @Override
//    public GetItemListResponse getItemList() {
//        return factory.itemListToResponse().apply(itemService.findItems());
//    }


    @Override
    public GetItemListResponse getItemListFromClothes(UUID id) {
        if (clothesService.findClothesById(id).isEmpty()) {
            throw new NotFoundException();
        }

        List<Item> items = itemService.findItemsByClothes(id);
        if (items.isEmpty()) {
            throw new NotFoundException();
        }
        return factory.itemListToResponse().apply(items);
    }

    @Override
    public GetItemListResponse getItemListFromUser(UUID id) {
        return itemService.findItemsByUser(id)
                .map(factory.itemListToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetItemResponse getItem(UUID id) {
        return itemService.findItemById(id)
                .map(factory.itemToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void patchItem(UUID id, PatchItemRequest request) {
        itemService.findItemById(id).ifPresentOrElse(
                entity -> {
                    itemService.update(factory.updateItemWithRequest().apply(entity, request));
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteItem(UUID id) {
        itemService.findItemById(id).ifPresentOrElse(
                entity -> itemService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
