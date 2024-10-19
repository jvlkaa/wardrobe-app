package pl.app.wardrobe.clothes.model.function;

import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.model.ClothesShortModel;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.io.Serializable;

public class ClothesShortToModelFunction implements Function<Clothes, ClothesShortModel>, Serializable {
    @Override
    public ClothesShortModel apply(Clothes entity){
        return ClothesShortModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
