package pl.app.wardrobe.clothes.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ItemEditModel;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ItemEdit implements Serializable {

    private final ItemService itemService;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private ItemEditModel item;

    @Inject
    public ItemEdit(ItemService service, ModelFunctionFactory factory) {
        this.itemService = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Item> item = itemService.findItemById(id);
        if (item.isPresent()) {
            this.item = factory.itemToEditModel().apply(item.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
        }
    }

    public String saveAction() {
        itemService.update(factory.updateItem().apply(itemService.findItemById(id).orElseThrow(), item));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
