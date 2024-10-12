package pl.app.wardrobe.clothes.dto;

import lombok.*;
import pl.app.wardrobe.clothes.entity.Material;

import java.util.List;

/* input user while adding new clothes category */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PutClothesRequest {
    String name;
    @Singular("material")
    private List<Material> material;
}
