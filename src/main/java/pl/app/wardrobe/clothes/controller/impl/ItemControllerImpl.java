package pl.app.wardrobe.clothes.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.app.wardrobe.clothes.controller.api.ItemController;
import pl.app.wardrobe.clothes.dto.GetItemListResponse;
import pl.app.wardrobe.clothes.dto.GetItemResponse;
import pl.app.wardrobe.clothes.dto.PatchItemRequest;
import pl.app.wardrobe.clothes.dto.PutItemRequest;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.controller.servlet.exception.NotFoundException;
import pl.app.wardrobe.controller.servlet.exception.ResourceConflictException;
import pl.app.wardrobe.factory.DtoFunctionFactory;

import java.util.List;
import java.util.UUID;

@RequestScoped
public class ItemControllerImpl implements ItemController {
    private final ItemService itemService;
    private final DtoFunctionFactory factory;

    @Inject
    public ItemControllerImpl(ItemService itemService, DtoFunctionFactory factory){
        this.itemService = itemService;
        this.factory = factory;
    }

    @Override
    public void putItem(UUID id, PutItemRequest request){
        try{
            itemService.create(factory.requestToItem().apply(id, request));
        }
        catch (IllegalArgumentException e){
            throw new ResourceConflictException();
        }
    }

    @Override
    public GetItemListResponse getItemList() {
        return factory.itemListToResponse().apply(itemService.findItems());
    }


    @Override
    public GetItemListResponse getItemListFromClothes(UUID id) {
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
                entity -> itemService.update(factory.updateItemWithRequest().apply(entity, request)),
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
