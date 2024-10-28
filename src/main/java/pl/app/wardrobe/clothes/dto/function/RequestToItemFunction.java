package pl.app.wardrobe.clothes.dto.function;

import pl.app.wardrobe.clothes.dto.PutItemRequest;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Item;

import java.util.UUID;

public class RequestToItemFunction implements TriFunction<UUID, UUID, PutItemRequest, Item> {
    @Override
    public Item apply (UUID clothesId,UUID id, PutItemRequest request){
        return Item.builder()
                .id(id)
                .name(request.getName())
                .size(request.getSize())
                .color(request.getColor())
                .purchaseDate(request.getPurchaseDate())
                .clothesCategory(Clothes.builder()
                        .id(clothesId)
                        .build())
                .build();
    }
}
