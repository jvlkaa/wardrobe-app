package pl.app.wardrobe.clothes.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.NoArgsConstructor;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.repository.api.ClothesRepository;
import pl.app.wardrobe.clothes.repository.api.ItemRepository;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.repository.api.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final ClothesRepository clothesRepository;
    private final UserRepository userRepository;

    @Inject
    public ItemService(ItemRepository itemRepository, ClothesRepository clothesRepository,
                       UserRepository userRepository){
        this.itemRepository = itemRepository;
        this.clothesRepository = clothesRepository;
        this.userRepository = userRepository;
    }

    /* CRUD order */
    @Transactional
    public void create(Item item) {
        if (itemRepository.find(item.getId()).isPresent()) {
            throw new BadRequestException("Item already exists.");
        }
        if (clothesRepository.find(item.getClothesCategory().getId()).isEmpty()) {
            throw new BadRequestException("Clothes does not exists.");
        }
        itemRepository.create(item);
        //update clothes because of cache
        clothesRepository.find(item.getClothesCategory().getId())
                .ifPresent(clothes -> clothes.getItems().add(item));
    }


    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public List<Item> findItemsFromUser(User user){
        return itemRepository.findByOwner(user);
    }


    public List<Item> findItemsByClothes(UUID id){
        return itemRepository.findByCategory(id);
    }

    public Optional<List<Item>> findItemsByUser(UUID id){
        return userRepository.find(id).map(itemRepository::findByOwner);
    }

    public Optional<Item> findItemById(UUID id){
        return itemRepository.find(id);
    }

    public Optional<Item> findItemByIdFromUser(User user, UUID id){
        return itemRepository.findByIdFromUser(user, id);
    }

    @Transactional
    public void update(Item item){
        itemRepository.update(item);
    }

    @Transactional
    public void delete(UUID id){
        //clothes (cache)
        itemRepository.find(id).ifPresent(item -> clothesRepository.find(item.getClothesCategory().getId()).
                ifPresent(clothes -> clothes.getItems().remove(item)));
        //item
        itemRepository.delete(itemRepository.find(id).orElseThrow());
    }
}
