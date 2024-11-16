package pl.app.wardrobe.clothes.model.function;

import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ItemEditModel;
import pl.app.wardrobe.user.entity.User;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateItemWithModelFunction implements BiFunction<Item, ItemEditModel, Item>, Serializable {
    @Override
    public Item apply(Item entity, ItemEditModel request){
        return Item.builder()
                .id(entity.getId())
                .name(request.getName())
                .size(entity.getSize())
                .color(entity.getColor())
                .purchaseDate(entity.getPurchaseDate())
                .clothesCategory(entity.getClothesCategory())
                .owner(User.builder()
                        .id(request.getOwner().getId())
                        .build())
                .build();
    }
}
