package pl.app.wardrobe.clothes.view;
import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ItemModel;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ItemView implements Serializable{

    private ItemService itemService;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private ItemModel item;

    @Inject
    public ItemView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public void init() throws IOException {
        Optional<Item> item = itemService.findItemByIdForCallerPrincipal(id);
        if (item.isPresent()) {
            this.item = factory.itemToModel().apply(item.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
        }
    }
}
