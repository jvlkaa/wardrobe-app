package pl.app.wardrobe.clothes.service;

import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.repository.api.ClothesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClothesService {
    private final ClothesRepository clothesRepository;

    public ClothesService(ClothesRepository clothesRepository){
        this.clothesRepository = clothesRepository;
    }

    /* CRUD order */
    public void create(Clothes clothes){
        clothesRepository.create(clothes);
    }

    public List<Clothes> findClothesList(){
        return clothesRepository.findClothesList();
    }

    public Optional<Clothes> findClothes(UUID id){
        return clothesRepository.findClothes(id);
    }
}
