package pl.app.wardrobe.clothes.model.converter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.model.ClothesShortModel;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = ClothesShortModel.class, managed = true)
public class ClothesModelConverter implements Converter<ClothesShortModel> {
    private final ClothesService clothesService;
    private final ModelFunctionFactory factory;

    @Inject
    public ClothesModelConverter(ClothesService clothesService, ModelFunctionFactory factory) {
        this.clothesService = clothesService;
        this.factory = factory;
    }

    @Override
    public ClothesShortModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Clothes> profession = clothesService.findClothesById(UUID.fromString(value));
        return profession.map(factory.clothesShortToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ClothesShortModel value) {
        return value == null ? "" : value.getId().toString();
    }

}
