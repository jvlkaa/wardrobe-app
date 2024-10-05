package pl.app.wardrobe.clothes.dto.function;

import pl.app.wardrobe.clothes.dto.GetClothesListResponse;
import pl.app.wardrobe.clothes.entity.Clothes;

import java.util.List;
import java.util.function.Function;

public class ClothesListToResponseFunction implements Function<List<Clothes>, GetClothesListResponse> {
    @Override
    public GetClothesListResponse apply (List<Clothes> entities){
        return GetClothesListResponse.builder()
                .clothes(entities.stream()
                        .map(clothes -> GetClothesListResponse.Clothes.builder()
                                .id(clothes.getId())
                                .name(clothes.getName())
                                .build())
                        .toList())
                .build();
    }
}
