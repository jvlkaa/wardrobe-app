package pl.app.wardrobe.clothes.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import pl.app.wardrobe.clothes.model.ItemListModel;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

@RequestScoped
@Named
public class ItemList {
    private ItemService itemService;

    private ItemListModel item_list;

    private final ModelFunctionFactory factory;

    @Inject
    public ItemList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }


    public ItemListModel getItem_list() {
        if (item_list == null) {
            item_list = factory.itemListToModel().apply(itemService.findItemsForCallerPrincipal());
        }
        return item_list;
    }

    public void  deleteAction(ItemListModel.Item item) {
        itemService.deleteForCallerPrincipal(item.getId());
        item_list = null;
    }

}
