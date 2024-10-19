package pl.app.wardrobe.clothes.view;
import java.io.IOException;
import java.io.Serializable;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ItemModel;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ItemView implements Serializable{

    private final ItemService itemService;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private ItemModel item;

    @Inject
    public ItemView(ItemService itemService, ModelFunctionFactory factory) {
        this.itemService = itemService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Item> item = itemService.findItemById(id);
        if (item.isPresent()) {
            this.item = factory.itemToModel().apply(item.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
        }
    }
}
