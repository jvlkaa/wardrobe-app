package pl.app.wardrobe.dtoFactory;

import pl.app.wardrobe.clothes.dto.PatchItemRequest;
import pl.app.wardrobe.clothes.dto.function.*;

public class DtoFunctionFactory {

    /* item */
    public ItemToResponseFunction itemToResponse(){
        return new ItemToResponseFunction();
    }
    public ItemListToResponseFunction itemListToResponse(){
        return new ItemListToResponseFunction();
    }
    public RequestToItemFunction requestToItem(){
        return new RequestToItemFunction();
    }
    public PatchItemRequest patchItemRequest(){
        return new PatchItemRequest();
    }
    public UpdateItemWithRequestFunction updateItemWithRequest(){
        return new UpdateItemWithRequestFunction();
    }

    /* clothes */
    public ClothesToResponseFunction clothesToResponse(){
        return new ClothesToResponseFunction();
    }
    public ClothesListToResponseFunction clothesListToResponse(){
        return new ClothesListToResponseFunction();
    }

    /* user */

}
