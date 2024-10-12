package pl.app.wardrobe.clothes.dto;

import lombok.*;
import pl.app.wardrobe.clothes.entity.Material;

import java.util.List;

/* input for the user - fields that can be updated  */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PatchClothesRequest {
    String name;
    @Singular("material")
    private List<Material> material;
}
