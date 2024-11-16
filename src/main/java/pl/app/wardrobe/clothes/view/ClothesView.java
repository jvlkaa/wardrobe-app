package pl.app.wardrobe.clothes.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ClothesModel;
import pl.app.wardrobe.clothes.model.ItemListModel;
import pl.app.wardrobe.clothes.model.ItemModel;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ClothesView implements Serializable {
    private ClothesService clothesService;
    private ItemService itemService;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private ClothesModel clothes;

    private ItemListModel clothes_item_list;


    @Inject
    public ClothesView(ModelFunctionFactory factory) {
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

    public void init() throws IOException {
        Optional<Clothes> clothes = clothesService.findClothesById(id);
        if (clothes.isPresent()) {
            this.clothes = factory.clothesToModel().apply(clothes.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Clothes not found");
        }
    }

    public ItemListModel getClothes_item_list() {
        if (clothes_item_list == null) {
            clothes_item_list = factory.itemListToModel().apply(itemService.findItemsByClothesForCallerPrincipal(clothes.getId()));
        }
        return clothes_item_list;
    }

    public String deleteAction(ItemListModel.Item item) {
        itemService.delete(item.getId());
        return "clothes_view?faces-redirect=true&id=" + id;
    }
}
