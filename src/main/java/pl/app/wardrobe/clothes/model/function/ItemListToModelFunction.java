package pl.app.wardrobe.clothes.model.function;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ItemListModel;
import java.util.List;
import java.util.function.Function;

public class ItemListToModelFunction implements Function<List<Item>, ItemListModel> {
    @Override
    public ItemListModel apply(List<Item> entity){
        return ItemListModel.builder()
            .itemList(entity.stream()
                .map(item -> ItemListModel.Item.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .lastModifiedDateTime(item.getLastModifiedDateTime())
                    .creationDateTime(item.getCreationDateTime())
                    .version(item.getVersion())
                    .build())
                .toList())
            .build();
    }
}
