package pl.app.wardrobe.clothes.model.function;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.model.ItemEditModel;
import pl.app.wardrobe.user.model.function.UserToModelFunction;

import java.io.Serializable;
import java.util.function.Function;

public class ItemToEditModelFunction implements Function<Item, ItemEditModel>, Serializable {
    private final UserToModelFunction userToModelFunction;

    public ItemToEditModelFunction(UserToModelFunction userToModelFunction){
        this.userToModelFunction = userToModelFunction;
    }
    @Override
    public ItemEditModel apply(Item entity){
        return ItemEditModel.builder()
                .name(entity.getName())
                .owner(userToModelFunction.apply(entity.getOwner()))
                .lastModifiedDateTime(entity.getLastModifiedDateTime())
                .creationDateTime(entity.getCreationDateTime())
                .version(entity.getVersion())
                .build();
    }
}
