package pl.app.wardrobe.clothes.dto.function;

import pl.app.wardrobe.clothes.dto.PatchItemRequest;
import pl.app.wardrobe.clothes.entity.Item;

import java.util.function.BiFunction;

public class UpdateItemWithRequestFunction implements BiFunction<Item, PatchItemRequest, Item> {
    @Override
    public Item apply(Item entity, PatchItemRequest request){
        return Item.builder()
                .id(entity.getId())
                .name(request.getName())
                .size(entity.getSize())
                .color(entity.getColor())
                .purchaseDate(entity.getPurchaseDate())
                .clothesCategory(entity.getClothesCategory())
                .owner(entity.getOwner())
                .creationDateTime(entity.getCreationDateTime())
                .build();
    }
}
