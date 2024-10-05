package pl.app.wardrobe.clothes.dto.function;

import pl.app.wardrobe.clothes.dto.PutItemRequest;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Item;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToItemFunction implements BiFunction<UUID, PutItemRequest, Item> {
    @Override
    public Item apply (UUID id, PutItemRequest request){
        return Item.builder()
                .id(id)
                .name(request.getName())
                .size(request.getSize())
                .color(request.getColor())
                .purchaseDate(request.getPurchaseDate())
                .clothesCategory(Clothes.builder()
                        .id(request.getClothesCategory())
                        .build())
                .build();
    }
}
