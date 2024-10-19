package pl.app.wardrobe.clothes.model;

import lombok.*;
import pl.app.wardrobe.clothes.entity.Material;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ClothesModel {
    private String name;
    private List<Material> material;
}
