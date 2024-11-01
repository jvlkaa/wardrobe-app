package pl.app.wardrobe.clothes.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.NoArgsConstructor;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.repository.api.ClothesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class ClothesService {
    private final ClothesRepository clothesRepository;

    @Inject
    public ClothesService(ClothesRepository clothesRepository){
        this.clothesRepository = clothesRepository;
    }

    /* CRUD order */
    @Transactional
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

    @Transactional
    public void update(Clothes clothes){
         clothesRepository.update(clothes);
    }

    @Transactional
    public void delete(UUID id){
        clothesRepository.delete(clothesRepository.find(id).orElseThrow());
    }

}
