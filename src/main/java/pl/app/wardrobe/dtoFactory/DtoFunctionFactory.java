package pl.app.wardrobe.dtofactory;

import pl.app.wardrobe.clothes.dto.PatchItemRequest;
import pl.app.wardrobe.clothes.dto.function.*;
import pl.app.wardrobe.user.dto.function.*;

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
    public UserToResponseFunction userToResponse(){
        return new UserToResponseFunction();
    }
    public UsersToResponseFunction usersToResponse(){
        return new UsersToResponseFunction();
    }

    public RequestToUserFunction requestToUser(){
        return new RequestToUserFunction();
    }

    public UpdateUserPasswordWithRequestFunction updateUserPasswordWithRequest(){
        return new UpdateUserPasswordWithRequestFunction();
    }

    public UpdateUserWithRequestFunction updateUserWithRequest(){
        return new UpdateUserWithRequestFunction();
    }
}
