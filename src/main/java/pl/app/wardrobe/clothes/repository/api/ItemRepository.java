package pl.app.wardrobe.clothes.repository.api;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.repository.api.Repository;
import pl.app.wardrobe.user.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends Repository<Item, UUID> {

    List<Item> findItems();
    List<Item> findItemsByCategory(Clothes category);
    List<Item> findItemsByOwner(User owner);
    Optional<Item> findByIdFromUser(User user, UUID id);

}
