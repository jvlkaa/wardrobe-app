package pl.app.wardrobe.clothes.view;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
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

    public String saveAction() throws IOException {
        try {
            itemService.updateForCallerPrincipal(factory.updateItem().apply(itemService.findItemById(id).orElseThrow(), item));
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";

        } catch (EJBException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                init();
                facesContext.addMessage(null, new FacesMessage("Version collision."));
            }
            return null ;
        }
    }


}
