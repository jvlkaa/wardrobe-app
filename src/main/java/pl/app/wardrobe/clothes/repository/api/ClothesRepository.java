package pl.app.wardrobe.clothes.repository.api;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.repository.api.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClothesRepository extends Repository<Clothes, UUID> {

}
