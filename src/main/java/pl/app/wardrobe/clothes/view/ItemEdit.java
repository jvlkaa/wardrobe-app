package pl.app.wardrobe.clothes.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ItemEditModel;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ItemEdit implements Serializable {

    private ItemService itemService;

    private final ModelFunctionFactory factory;

    private final FacesContext facesContext;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private ItemEditModel item;

    @Inject
    public ItemEdit(ModelFunctionFactory factory, FacesContext facesContext) {
        this.factory = factory;
        this.facesContext = facesContext;
    }

    @EJB
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public void init() throws IOException {
        Optional<Item> item = itemService.findItemByIdForCallerPrincipal(id);
        if (item.isPresent()) {
            this.item = factory.itemToEditModel().apply(item.get());
        } else {
            facesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
        }
    }

    public String saveAction() {
        itemService.updateForCallerPrincipal(factory.updateItem().apply(itemService.findItemById(id).orElseThrow(), item));
        return "/item/item_list.xhtml?faces-redirect=true&includeViewParams=true";
    }

}
