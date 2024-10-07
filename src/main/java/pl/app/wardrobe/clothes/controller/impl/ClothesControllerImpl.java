package pl.app.wardrobe.clothes.controller.impl;

import pl.app.wardrobe.clothes.controller.api.ClothesController;
import pl.app.wardrobe.clothes.dto.GetClothesListResponse;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.dtofactory.DtoFunctionFactory;

public class ClothesControllerImpl implements ClothesController {

    private final ClothesService clothesService;
    private final DtoFunctionFactory factory;

    public ClothesControllerImpl(ClothesService clothesService, DtoFunctionFactory dtoFunctionFactory){
        this.clothesService = clothesService;
        this.factory = dtoFunctionFactory;
    }
    @Override
    public GetClothesListResponse getClothesList() {
        return factory.clothesListToResponse().apply(clothesService.findClothesList());
    }
}
