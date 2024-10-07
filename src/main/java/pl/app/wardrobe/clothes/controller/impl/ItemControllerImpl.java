package pl.app.wardrobe.clothes.controller.impl;

import pl.app.wardrobe.clothes.controller.api.ItemController;
import pl.app.wardrobe.clothes.dto.GetItemListResponse;
import pl.app.wardrobe.clothes.dto.GetItemResponse;
import pl.app.wardrobe.clothes.dto.PatchItemRequest;
import pl.app.wardrobe.clothes.dto.PutItemRequest;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.controller.servlet.exception.NotFoundException;
import pl.app.wardrobe.dtofactory.DtoFunctionFactory;

import java.io.InputStream;
import java.util.UUID;

public class ItemControllerImpl implements ItemController {
    private final ItemService itemService;
    private final DtoFunctionFactory factory;

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
            throw new NotFoundException();
        }
    }

    @Override
    public GetItemListResponse getItemList() {
        return factory.itemListToResponse().apply(itemService.findItems());
    }

    @Override
    public GetItemListResponse getClothesItemList(UUID id) {
        return itemService.findItemsByClothes(id)
                .map(factory.itemListToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetItemListResponse getUserItemList(UUID id) {
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

//    @Override
//    public byte[] getItemPhoto(UUID id) {
//        return itemService.findItemById(id)
//                .map(Item::getAvatar)
//                .orElseThrow(NotFoundException::new);
//    }

    @Override
    public void patchItem(UUID id, PatchItemRequest request) {
        itemService.findItemById(id).ifPresentOrElse(
                entity -> itemService.update(factory.updateItemWithRequest().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

//    @Override
//    public void patchItemPhoto(UUID id, InputStream photo) {
//        itemService.findItemById(id).ifPresentOrElse(
//                entity -> itemService.updatePhoto(id, photo),
//                () -> {
//                    throw new NotFoundException();
//                }
//        );
//    }

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
