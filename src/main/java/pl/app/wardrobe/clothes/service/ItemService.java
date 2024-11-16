package pl.app.wardrobe.clothes.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import jakarta.security.enterprise.SecurityContext;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.repository.api.ClothesRepository;
import pl.app.wardrobe.clothes.repository.api.ItemRepository;
import pl.app.wardrobe.user.entity.Role;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.repository.api.UserRepository;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@RolesAllowed(Role.USER)
public class ItemService {
    private final ItemRepository itemRepository;
    private final ClothesRepository clothesRepository;
    private final UserRepository userRepository;
    private final SecurityContext securityContext;

    @Inject
    public ItemService(ItemRepository itemRepository, ClothesRepository clothesRepository,
                       UserRepository userRepository, @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext){
        this.itemRepository = itemRepository;
        this.clothesRepository = clothesRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }


    /* CRUD order */
    @RolesAllowed(Role.ADMIN)
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

    public void createForCallerPrincipal(Item item) {
        if (securityContext.isCallerInRole(Role.ADMIN)) {
            create(item);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        item.setOwner(user);
        create(item);
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

    public List<Item>findItemsByUserAndClothes(User user , Clothes clothes){
        return itemRepository.findByCategoryAndUser(user, clothes);
    }

    public Optional<Item> findItemByIdForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(Role.ADMIN)) {
            return findItemById(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(EJBAccessException::new);
        return findItemByIdFromUser(user, id);
    }

    public List<Item> findItemsForCallerPrincipal() {
        if (securityContext.isCallerInRole(Role.ADMIN)) {
            return findItems();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(EJBAccessException::new);
        return findItemsByUser(user.getId()).get();
    }

    public List<Item> findItemsByClothesForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(Role.ADMIN)) {
            return findItemsByClothes(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(EJBAccessException::new);
        return findItemsByUserAndClothes(user, clothesRepository.find(id).get());
    }

    public Optional<List<Item>> findItemsByUserForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(Role.ADMIN)) {
            return findItemsByUser(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(EJBAccessException::new);
        return findItemsByUser(user.getId());
    }

    public void update(Item item){
        itemRepository.update(item);
    }

    public void updateForCallerPrincipal(Item item) {
        if (securityContext.isCallerInRole(Role.ADMIN)) {
            update(item);
        }
        else {
            User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                    .orElseThrow(EJBAccessException::new);
            if (item.getOwner().getId() == user.getId()) {
                update(item);
            } else throw new EJBAccessException();
        }
    }

    public void delete(UUID id){
        //clothes (cache)
        itemRepository.find(id).ifPresent(item -> clothesRepository.find(item.getClothesCategory().getId()).
                ifPresent(clothes -> clothes.getItems().remove(item)));
        //item
        itemRepository.delete(itemRepository.find(id).orElseThrow());
    }

    public void deleteForCallerPrincipal(UUID id){
        if (securityContext.isCallerInRole(Role.ADMIN)) {
            delete(id);
        }
        else {
            User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                    .orElseThrow(EJBAccessException::new);
            findItemById(id).ifPresent(item -> {
                if (item.getOwner().getId().equals(user.getId())) {
                    delete(id);
                } else throw new EJBAccessException();
            });
        }
    }
}
