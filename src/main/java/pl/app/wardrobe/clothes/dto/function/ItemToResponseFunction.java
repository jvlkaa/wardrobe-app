package pl.app.wardrobe.clothes.dto.function;
import pl.app.wardrobe.clothes.dto.GetItemResponse;
import pl.app.wardrobe.clothes.entity.Item;
import java.util.function.Function;

public class ItemToResponseFunction implements Function<Item, GetItemResponse> {
    @Override
    public GetItemResponse apply(Item entity){
        return GetItemResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .size(entity.getSize())
                .color(entity.getColor())
                .purchaseDate(entity.getPurchaseDate())
                .clothesCategory(GetItemResponse.Clothes.builder()
                        .id(entity.getClothesCategory().getId())
                        .name(entity.getClothesCategory().getName())
                        .build())
                .version(entity.getVersion())
                .build();
    }
}
