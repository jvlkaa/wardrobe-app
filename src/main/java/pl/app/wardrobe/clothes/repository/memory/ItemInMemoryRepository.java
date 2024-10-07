package pl.app.wardrobe.clothes.repository.memory;

import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.repository.api.ItemRepository;
import pl.app.wardrobe.datasource.DataSource;
import pl.app.wardrobe.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemInMemoryRepository implements ItemRepository {

    private final DataSource dataSource;

    public ItemInMemoryRepository(DataSource source){
        this.dataSource = source;
    }

    @Override
    public void create(Item entity) {
        dataSource.createItem(entity);
    }

    @Override
    public List<Item> findItems() {
        return dataSource.findItemList();
    }
    @Override
    public List<Item> findAll() {
        return dataSource.findItemList();
    }

    @Override
    public Optional<Item> find(UUID primaryKey) {
        return dataSource.findItemList().stream()
                .filter(item -> item.getId().equals(primaryKey))
                .findFirst();
    }

    @Override
    public List<Item> findItemsByCategory(Clothes category) {
        return dataSource.findItemList().stream()
                .filter(item -> category.equals(item.getClothesCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> findItemsByOwner(User owner) {
        return dataSource.findItemList().stream()
                .filter(item -> owner.equals(item.getOwner()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findByIdFromUser(User user, UUID id) {
        return dataSource.findItemList().stream()
                .filter(item -> item.getOwner().equals(user))
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }


    @Override
    public void update(Item entity) {
        dataSource.updateItem(entity);
    }

    @Override
    public void delete(Item entity) {
        dataSource.deleteItem(entity.getId());
    }
}
