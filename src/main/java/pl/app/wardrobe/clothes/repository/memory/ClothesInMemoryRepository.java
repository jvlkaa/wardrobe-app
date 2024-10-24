package pl.app.wardrobe.clothes.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.repository.api.ClothesRepository;
import pl.app.wardrobe.datasource.component.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class ClothesInMemoryRepository implements ClothesRepository {
    private final DataSource dataSource;

    @Inject
    public ClothesInMemoryRepository(DataSource source){
        this.dataSource = source;
    }


    @Override
    public void create(Clothes entity) {
        dataSource.createClothes(entity);
    }

    @Override
    public List<Clothes> findAll() {
        return dataSource.findClothesList();
    }

    @Override
    public Optional<Clothes> find(UUID primaryKey) {
        return dataSource.findClothesList().stream()
                .filter(clothes -> clothes.getId().equals(primaryKey))
                .findFirst();
    }

    @Override
    public void update(Clothes entity) {
        dataSource.updateClothes(entity);
    }

    @Override
    public void delete(Clothes entity) {

        dataSource.deleteClothes(entity.getId());
    }
}
