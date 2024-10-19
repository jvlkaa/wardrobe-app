package pl.app.wardrobe.clothes.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.app.wardrobe.clothes.controller.api.ClothesController;
import pl.app.wardrobe.clothes.dto.GetClothesListResponse;
import pl.app.wardrobe.clothes.dto.GetClothesResponse;
import pl.app.wardrobe.clothes.dto.PatchClothesRequest;
import pl.app.wardrobe.clothes.dto.PutClothesRequest;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.controller.servlet.exception.NotFoundException;
import pl.app.wardrobe.controller.servlet.exception.ResourceConflictException;
import pl.app.wardrobe.factory.DtoFunctionFactory;

import java.util.UUID;

@RequestScoped
public class ClothesControllerImpl implements ClothesController {

    private final ClothesService clothesService;
    private final ItemService itemService;
    private final DtoFunctionFactory factory;

    @Inject
    public ClothesControllerImpl(ClothesService clothesService, ItemService itemService, DtoFunctionFactory dtoFunctionFactory){
        this.clothesService = clothesService;
        this.itemService = itemService;
        this.factory = dtoFunctionFactory;
    }

    @Override
    public void putClothes(UUID id, PutClothesRequest request) {
        try{
            clothesService.create(factory.requestToClothes().apply(id, request));
        }
        catch (IllegalArgumentException e){
            throw new ResourceConflictException();
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
                entity -> {
                    itemService.findItemsByClothes(id).ifPresentOrElse(
                            items -> items.forEach( item -> itemService.delete(item.getId())),
                            () -> {
                                throw new NotFoundException();
                            }
                    );
                    clothesService.delete(id);
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
