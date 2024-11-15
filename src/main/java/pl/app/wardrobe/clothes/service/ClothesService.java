package pl.app.wardrobe.clothes.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import lombok.NoArgsConstructor;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.repository.api.ClothesRepository;

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
    public void create(Clothes clothes){
        if (clothesRepository.find(clothes.getId()).isPresent()) {
            throw new BadRequestException("Clothes already exists.");
        }
        clothesRepository.create(clothes);
    }

    public List<Clothes> findClothesList(){
        return clothesRepository.findAll();
    }

    public Optional<Clothes> findClothesById(UUID id){
        return clothesRepository.find(id);
    }

    public void update(Clothes clothes){
         clothesRepository.update(clothes);
    }

    public void delete(UUID id){
        clothesRepository.delete(clothesRepository.find(id).orElseThrow());
    }

}
