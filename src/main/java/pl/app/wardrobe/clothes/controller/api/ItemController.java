package pl.app.wardrobe.clothes.controller.api;

import pl.app.wardrobe.clothes.dto.GetItemListResponse;
import pl.app.wardrobe.clothes.dto.GetItemResponse;
import pl.app.wardrobe.clothes.dto.PatchItemRequest;
import pl.app.wardrobe.clothes.dto.PutItemRequest;

import java.io.InputStream;
import java.util.UUID;

public interface ItemController {
    void putItem(UUID id, PutItemRequest request);

    GetItemListResponse getItemList();
    GetItemResponse getItem(UUID id);
    GetItemListResponse getItemListFromClothes(UUID id);
    GetItemListResponse getItemListFromUser(UUID id);

    void patchItem(UUID id, PatchItemRequest request);

    void deleteItem(UUID id);
}
