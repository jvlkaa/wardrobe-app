package pl.app.wardrobe.clothes.model.function;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ItemEditModel;
import java.io.Serializable;
import java.util.function.Function;

public class ItemToEditModelFunction implements Function<Item, ItemEditModel>, Serializable {
    @Override
    public ItemEditModel apply(Item entity){
        return ItemEditModel.builder()
                .name(entity.getName())
                .build();
    }
}
