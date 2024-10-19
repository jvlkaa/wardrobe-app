package pl.app.wardrobe.clothes.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.app.wardrobe.clothes.model.ClothesListModel;
import pl.app.wardrobe.clothes.model.ItemListModel;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

@RequestScoped
@Named
public class ClothesList {
    private final ClothesService clothesService;

    private ClothesListModel clothes_list;

    private final ModelFunctionFactory factory;

    @Inject
    public ClothesList(ClothesService clothesService, ModelFunctionFactory factory) {
        this.clothesService = clothesService;
        this.factory = factory;
    }


    public ClothesListModel getClothes_list() {
        if (clothes_list == null) {
            clothes_list = factory.clothesListToModel().apply(clothesService.findClothesList());
        }
        return clothes_list;
    }

    public String deleteAction(ClothesListModel.Clothes clothes) {
        clothesService.delete(clothes.getId());
        return "clothes_list?faces-redirect=true";
    }
}
