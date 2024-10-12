package pl.app.wardrobe.datasource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.serialization.CloningComponent;
import pl.app.wardrobe.user.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/* alternative for database to present servlet */
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataSource {

    /* entities */
    private final Set<Item> items = new HashSet<>();
    private final Set<Clothes> clothes = new HashSet<>();
    private final Set<User> users = new HashSet<>();
    /* serialization */
    private final CloningComponent serialization;

    @Inject
    public DataSource(CloningComponent serialization){
        this.serialization = serialization;
    }

    /* item */
    /* CRUD order */
    public synchronized void createItem(Item newItem) throws IllegalArgumentException {
        if (items.stream().anyMatch(item -> item.getId().equals(newItem.getId()))) {
            throw new IllegalArgumentException("Id is not unique");
        }
        Item entity = cloneWithRelationships(newItem);
        items.add(entity);

    }

    public synchronized List<Item> findItemList() {
        return items.stream()
                .map(serialization::clone)
                .collect(Collectors.toList());
    }

    public synchronized void updateItem(Item updateItem) throws IllegalArgumentException {
        Item entity = cloneWithRelationships(updateItem);
        if (items.removeIf(item -> item.getId().equals(updateItem.getId()))) {
            items.add(entity);
        } else {
            throw new IllegalArgumentException("Item not found");
        }
    }

    public synchronized void deleteItem(UUID id) throws IllegalArgumentException {
        if (!items.removeIf(item -> item.getId().equals(id))) {
            throw new IllegalArgumentException("Item not found");
        }
    }

    /* clothes */
    /* CRUD order */
    public synchronized void createClothes(Clothes createClothes) throws IllegalArgumentException {
        if (clothes.stream().anyMatch(clothes -> clothes.getId().equals(createClothes.getId()))) {
            throw new IllegalArgumentException("Id is not unique");
        }
        clothes.add(serialization.clone(createClothes));
    }

    public synchronized List<Clothes> findClothesList() {
        return clothes.stream()
                .map(serialization::clone)
                .collect(Collectors.toList());
    }

    public synchronized void updateClothes(Clothes updateClothes) throws IllegalArgumentException {
        if (clothes.removeIf(clothesItem -> clothesItem.getId().equals(updateClothes.getId()))) {
            clothes.add(serialization.clone(updateClothes));
        } else {
            throw new IllegalArgumentException("clothes not found");
        }
    }

    public synchronized void deleteClothes(UUID id )throws IllegalArgumentException {
        if (!clothes.removeIf(clothes -> clothes.getId().equals(id))) {
            throw new IllegalArgumentException("Clothes not found");
        }
    }

    /* user */
    /* CRUD order */
    public synchronized void createUser(User createUser) throws IllegalArgumentException {
        if (users.stream().anyMatch(item -> item.getId().equals(createUser.getId()))) {
            throw new IllegalArgumentException("Is is not unique");
        }
        users.add(serialization.clone(createUser));
    }

    public synchronized List<User> findUserList() {
        return users.stream()
                .map(serialization::clone)
                .collect(Collectors.toList());
    }


    public synchronized void updateUser(User updateUser) throws IllegalArgumentException {
        if (users.removeIf(item -> item.getId().equals(updateUser.getId()))) {
            users.add(serialization.clone(updateUser));
        } else {
            throw new IllegalArgumentException("user not found");
        }
    }

    public synchronized void deleteUser(UUID id )throws IllegalArgumentException {
        if (!users.removeIf(user -> user.getId().equals(id))) {
            throw new IllegalArgumentException("User not found");
        }
    }

    private Item cloneWithRelationships(Item item) {
        Item entity = serialization.clone(item);
        if (entity.getOwner() != null) {
            entity.setOwner(users.stream()
                    .filter(user -> user.getId().equals(item.getOwner().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("User not found")));
        }

        if (entity.getClothesCategory() != null) {
            entity.setClothesCategory(clothes.stream()
                    .filter(clothes -> clothes.getId().equals(item.getClothesCategory().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Clothes not found")));
        }

        return entity;
    }
}
