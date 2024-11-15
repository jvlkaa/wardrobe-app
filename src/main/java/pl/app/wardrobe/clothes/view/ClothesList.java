package pl.app.wardrobe.clothes.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.NotFoundException;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ClothesListModel;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

import java.util.List;

@RequestScoped
@Named
public class ClothesList {
    private ClothesService clothesService;
    private ItemService itemService;
    private ClothesListModel clothes_list;

    private final ModelFunctionFactory factory;

    @Inject
    public ClothesList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setClothesService(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @EJB
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
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
