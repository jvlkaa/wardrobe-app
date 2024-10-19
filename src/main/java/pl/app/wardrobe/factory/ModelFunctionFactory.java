package pl.app.wardrobe.factory;

import jakarta.enterprise.context.ApplicationScoped;
import pl.app.wardrobe.clothes.model.function.*;

@ApplicationScoped
public class ModelFunctionFactory {

    public ItemToModelFunction itemToModel(){
        return new ItemToModelFunction();
    }

    public ItemListToModelFunction itemListToModel(){
        return new ItemListToModelFunction();
    }

    public ItemToEditModelFunction itemToEditModel(){
        return new ItemToEditModelFunction();
    }

    public ModelToItemFunction modelToItem(){
        return new ModelToItemFunction();
    }

    public ClothesShortToModelFunction clothesShortToModel(){
        return new ClothesShortToModelFunction();
    }

    public UpdateItemWithModelFunction updateItem(){
        return new UpdateItemWithModelFunction();
    }

    public ClothesToModelFunction clothesToModel(){
        return new ClothesToModelFunction();
    }

    public ClothesListToModelFunction clothesListToModel(){
        return new ClothesListToModelFunction();
    }
}
