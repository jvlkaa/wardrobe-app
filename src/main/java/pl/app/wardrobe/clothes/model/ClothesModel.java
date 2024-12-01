package pl.app.wardrobe.clothes.model;

import lombok.*;
import pl.app.wardrobe.clothes.entity.Material;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ClothesModel implements Serializable {
    private UUID id;
    private String name;
    private List<Material> material;
}
