package pl.app.wardrobe.clothes.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import lombok.NoArgsConstructor;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.repository.api.ClothesRepository;
import pl.app.wardrobe.user.entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class ClothesService {
    private final ClothesRepository clothesRepository;

    @Inject
    public ClothesService(ClothesRepository clothesRepository){
        this.clothesRepository = clothesRepository;
    }

    /* CRUD order */
    @RolesAllowed(Role.ADMIN)
    public void create(Clothes clothes){
        if (clothesRepository.find(clothes.getId()).isPresent()) {
            throw new BadRequestException("Clothes already exists.");
        }
        clothesRepository.create(clothes);
    }

    @RolesAllowed(Role.USER)
    public List<Clothes> findClothesList(){
        return clothesRepository.findAll();
    }

    @RolesAllowed(Role.USER)
    public Optional<Clothes> findClothesById(UUID id){
        return clothesRepository.find(id);
    }

    @RolesAllowed(Role.ADMIN)
    public void update(Clothes clothes){
         clothesRepository.update(clothes);
    }

    @RolesAllowed(Role.ADMIN)
    public void delete(UUID id){
        clothesRepository.delete(clothesRepository.find(id).orElseThrow());
    }

}
