package pl.app.wardrobe.clothes.model.function;

import pl.app.wardrobe.clothes.entity.Material;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.model.ClothesModel;

import java.io.Serializable;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClothesToModelFunction  implements Function<Clothes, ClothesModel>, Serializable {
    @Override
    public ClothesModel apply(Clothes entity){
        return ClothesModel.builder()
                .name(entity.getName())
                .material(entity.getMaterial().stream()
                        .map(material -> Material.valueOf(material.name()))
                        .collect(Collectors.toList()))
                .build();
    }
}
