package pl.app.wardrobe.clothes.model.function;

import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ItemModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.Function;

public class ItemToModelFunction  implements Function<Item, ItemModel>, Serializable {
    @Override
    public ItemModel apply(Item entity){
        return ItemModel.builder()
                .name(entity.getName())
                .size(entity.getSize().toString())
                .color(entity.getColor())
                .purchaseDate(entity.getPurchaseDate())
                .clothesCategory(entity.getClothesCategory().getName())
                .build();
    }
}
