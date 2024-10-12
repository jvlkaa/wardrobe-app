package pl.app.wardrobe.clothes.controller.api;

import pl.app.wardrobe.clothes.dto.GetClothesListResponse;
import pl.app.wardrobe.clothes.dto.GetClothesResponse;
import pl.app.wardrobe.clothes.dto.PatchClothesRequest;
import pl.app.wardrobe.clothes.dto.PutClothesRequest;

import java.util.UUID;

public interface ClothesController {
    void putClothes(UUID id, PutClothesRequest request);
    GetClothesListResponse getClothesList();
    GetClothesResponse getClothes(UUID id);
    void patchClothes(UUID id, PatchClothesRequest request);
    void deleteClothes(UUID id);
}
