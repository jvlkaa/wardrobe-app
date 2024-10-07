package pl.app.wardrobe.clothes.repository.memory;

import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.repository.api.ClothesRepository;
import pl.app.wardrobe.datasource.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClothesInMemoryRepository implements ClothesRepository {
    private final DataSource dataSource;

    public ClothesInMemoryRepository(DataSource source){
        this.dataSource = source;
    }


    @Override
    public void create(Clothes entity) {
        dataSource.createClothes(entity);
    }

    @Override
    public List<Clothes> findClothesList() {
        return dataSource.findClothesList();
    }

    @Override
    public Optional<Clothes> findClothes(UUID id) {
        return dataSource.findClothesList().stream()
                .filter(clothes -> clothes.getId().equals(id))
                .findFirst();
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
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void delete(Clothes entity) {
        throw new UnsupportedOperationException("not implemented");
    }
}
