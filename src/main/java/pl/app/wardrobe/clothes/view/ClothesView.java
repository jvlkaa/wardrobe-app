package pl.app.wardrobe.clothes.view;

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
import pl.app.wardrobe.clothes.model.ItemModel;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ClothesView implements Serializable {
    private final ClothesService clothesService;
    private final ItemService itemService;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private ClothesModel clothes;

    @Inject
    public ClothesView( ClothesService clothesService, ItemService itemService, ModelFunctionFactory factory) {
        this.clothesService = clothesService;
        this.itemService = itemService;
        this.factory = factory;
    }


    public void init() throws IOException {
        Optional<Clothes> clothes = clothesService.findClothesById(id);
        if (clothes.isPresent()) {
            this.clothes = factory.clothesToModel().apply(clothes.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Clothes not found");
        }
    }
}
