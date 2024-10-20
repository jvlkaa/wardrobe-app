package pl.app.wardrobe.clothes.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ClothesListModel;
import pl.app.wardrobe.clothes.model.ItemListModel;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.controller.servlet.exception.NotFoundException;
import pl.app.wardrobe.factory.ModelFunctionFactory;

import java.util.List;

@RequestScoped
@Named
public class ClothesList {
    private final ClothesService clothesService;
    private final ItemService itemService;
    private ClothesListModel clothes_list;

    private final ModelFunctionFactory factory;

    @Inject
    public ClothesList(ClothesService clothesService,  ItemService itemService, ModelFunctionFactory factory) {
        this.clothesService = clothesService;
        this.itemService = itemService;
        this.factory = factory;
    }


    public ClothesListModel getClothes_list() {
        if (clothes_list == null) {
            clothes_list = factory.clothesListToModel().apply(clothesService.findClothesList());
        }
        return clothes_list;
    }

    public String deleteAction(ClothesListModel.Clothes clothes) {
        clothesService.findClothesById(clothes.getId()).ifPresentOrElse(
                entity -> {
                    List<Item> items = itemService.findItemsByClothes(clothes.getId());
                    if (items.isEmpty()) {
                        throw new NotFoundException();
                    }
                    items.forEach(item -> itemService.delete(item.getId()));

                    clothesService.delete(clothes.getId());
                },
                () -> {
                    throw new NotFoundException();
                }
        );

        return "clothes_list?faces-redirect=true";
    }
}
