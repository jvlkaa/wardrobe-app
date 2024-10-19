package pl.app.wardrobe.clothes.model.function;

import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ClothesListModel;
import pl.app.wardrobe.clothes.model.ItemListModel;

import java.util.List;
import java.util.function.Function;

public class ClothesListToModelFunction implements Function<List<Clothes>, ClothesListModel> {
    @Override
    public ClothesListModel apply(List<Clothes> entity){
        return ClothesListModel.builder()
                .clothesList(entity.stream()
                        .map(clothes -> ClothesListModel.Clothes.builder()
                                .id(clothes.getId())
                                .name(clothes.getName())
                                .build())
                        .toList())
                .build();
    }
}
