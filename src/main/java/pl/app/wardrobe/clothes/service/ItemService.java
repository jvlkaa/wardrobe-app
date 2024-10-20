package pl.app.wardrobe.clothes.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.app.wardrobe.clothes.entity.Clothes;
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
    public void create(Item item){
        itemRepository.create(item);
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

    public void update(Item item){
        itemRepository.update(item);
    }


    public void delete(UUID id){
        itemRepository.delete(itemRepository.find(id).orElseThrow());
    }
}
