package pl.app.wardrobe.clothes.model.function;

import lombok.SneakyThrows;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.entity.Size;
import pl.app.wardrobe.clothes.model.ItemCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToItemFunction implements Function<ItemCreateModel, Item>, Serializable {
    @Override
    @SneakyThrows
    public Item apply(ItemCreateModel model){
        return Item.builder()
                .id(model.getId())
                .name(model.getName())
                .size(Size.valueOf(model.getSize()))
                .color(model.getColor())
                .purchaseDate(model.getPurchaseDate())
                .clothesCategory(Clothes.builder()
                        .id(model.getClothesCategory().getId())
                        .build())
                .build();

    }
}
