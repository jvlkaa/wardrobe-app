package pl.app.wardrobe.clothes.dto.function;

import pl.app.wardrobe.clothes.dto.GetClothesResponse;
import pl.app.wardrobe.clothes.dto.PutClothesRequest;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Material;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class RequestToClothesFunction implements BiFunction<UUID, PutClothesRequest, Clothes> {
    @Override
    public Clothes apply (UUID id, PutClothesRequest request){
            return Clothes.builder()
            .id(id)
            .name(request.getName())
            .material(request.getMaterial().stream()
                    .map(material -> Material.valueOf(material.name()))
                    .collect(Collectors.toList()))
            .build();
    }
}
