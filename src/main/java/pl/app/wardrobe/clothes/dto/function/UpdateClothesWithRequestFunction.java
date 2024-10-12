package pl.app.wardrobe.clothes.dto.function;

import pl.app.wardrobe.clothes.dto.PatchClothesRequest;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Material;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class UpdateClothesWithRequestFunction implements BiFunction<Clothes, PatchClothesRequest, Clothes> {
    @Override
    public Clothes apply(Clothes entity, PatchClothesRequest request){
        return Clothes.builder()
                .id(entity.getId())
                .name(request.getName())
                .material((request.getMaterial().stream()
                        .map(material -> Material.valueOf(material.name()))
                        .collect(Collectors.toList())))
                .build();
    }

}
