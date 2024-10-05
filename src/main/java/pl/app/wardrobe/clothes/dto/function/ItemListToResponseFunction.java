package pl.app.wardrobe.clothes.dto.function;

import pl.app.wardrobe.clothes.dto.GetItemListResponse;
import pl.app.wardrobe.clothes.entity.Item;
import java.util.List;
import java.util.function.Function;

public class ItemListToResponseFunction implements Function<List<Item>, GetItemListResponse>{
    @Override
    public GetItemListResponse apply(List<Item> entities){
        return GetItemListResponse.builder()
                .items(entities.stream()
                    .map(item -> GetItemListResponse.Item.builder()
                            .id(item.getId())
                            .name(item.getName())
                            .build())
                    .toList())
                .build();
    }
}
