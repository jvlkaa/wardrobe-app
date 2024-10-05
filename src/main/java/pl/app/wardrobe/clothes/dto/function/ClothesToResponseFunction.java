package pl.app.wardrobe.clothes.dto.function;

import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.dto.GetClothesResponse;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClothesToResponseFunction implements Function<Clothes, GetClothesResponse> {
    @Override
    public GetClothesResponse apply(Clothes entity){
        return GetClothesResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .material(entity.getMaterial().stream()
                        .map(material -> GetClothesResponse.Material.valueOf(material.name()))
                        .collect(Collectors.toList()))
                .build();
    }
}
