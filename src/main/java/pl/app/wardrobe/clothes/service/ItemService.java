package pl.app.wardrobe.clothes.service;

import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.repository.api.ClothesRepository;
import pl.app.wardrobe.clothes.repository.api.ItemRepository;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ItemService {
    private final ItemRepository itemRepository;
    private final ClothesRepository clothesRepository;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, ClothesRepository clothesRepository,
                       UserRepository userRepository){
        this.itemRepository = itemRepository;
        this.clothesRepository = clothesRepository;
        this.userRepository = userRepository;
    }

    /* CRUD order */
    public void create(Item item){
        itemRepository.create(item);
    }

    public List<Item> findItems(){
        return itemRepository.findItems();
    }

    public List<Item> findItemsFromUser(User user){
        return itemRepository.findItemsByOwner(user);
    }

    public Optional<List<Item>> findItemsByClothes(UUID id){
        return clothesRepository.find(id).map(itemRepository::findItemsByCategory);
    }

    public Optional<List<Item>> findItemsByUser(UUID id){
        return userRepository.find(id).map(itemRepository::findItemsByOwner);
    }

    public Optional<Item> findItemById(UUID id){
        return itemRepository.find(id);
    }

    public Optional<Item> findItemByIdFromUser(User user, UUID id){
        return itemRepository.findByIdFromUser(user, id);
    }

    public void update(Item item){
        itemRepository.update(item);
    }


    public void delete(UUID id){
        itemRepository.delete(itemRepository.find(id).orElseThrow());
    }
}
