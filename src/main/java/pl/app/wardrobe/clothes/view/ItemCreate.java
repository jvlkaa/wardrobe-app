package pl.app.wardrobe.clothes.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Size;
import pl.app.wardrobe.clothes.model.ClothesShortModel;
import pl.app.wardrobe.clothes.model.ItemCreateModel;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.factory.ModelFunctionFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class ItemCreate implements Serializable {

    private ItemService itemService;
    private ClothesService clothesService;
    private final ModelFunctionFactory factory;

    @Getter
    private ItemCreateModel item;
    @Getter
    private List<ClothesShortModel> clothes_list;

    private final Conversation conversation;

    @Inject
    public ItemCreate(
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.factory = factory;
        this.conversation = conversation;
    }

    @EJB
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @EJB
    public void setClothesService(ClothesService clothesService) {
        this.clothesService = clothesService;
    }


    public void init() {
        if (conversation.isTransient()) {
            clothes_list = clothesService.findClothesList().stream()
                    .map(factory.clothesShortToModel())
                    .collect(Collectors.toList());
            item = ItemCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    /* navigation */
    public Object goToBasicAction() {
        return "/item/item_create.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/item/item_create_confirm.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/item/item_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        itemService.create(factory.modelToItem().apply(item));
        conversation.end();
        return "/item/item_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }

    public List<Size> getAvailableSizes() {
        return Arrays.asList(Size.values());
    }

}
