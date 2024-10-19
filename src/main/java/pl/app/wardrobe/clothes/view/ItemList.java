package pl.app.wardrobe.clothes.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import pl.app.wardrobe.clothes.model.ItemListModel;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

@RequestScoped
@Named
public class ItemList {
    private final ItemService itemService;

    private ItemListModel item_list;

    private final ModelFunctionFactory factory;

    @Inject
    public ItemList(ItemService service, ModelFunctionFactory factory) {
        this.itemService = service;
        this.factory = factory;
    }


    public ItemListModel getItem_list() {
        if (item_list == null) {
            item_list = factory.itemListToModel().apply(itemService.findItems());
        }
        return item_list;
    }

    public String deleteAction(ItemListModel.Item item) {
        itemService.delete(item.getId());
        return "item_list?faces-redirect=true";
    }

}
