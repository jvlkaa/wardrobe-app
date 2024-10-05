package pl.app.wardrobe.clothes.dto;

import lombok.*;
import pl.app.wardrobe.clothes.entity.Material;

import java.util.List;
import java.util.UUID;

/* clothes response */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GetClothesResponse {

    public enum Material {
        DENIM,
        COTTON,
        POLYESTER,
        WOOL,
        LEATHER,
        SILK,
        LINEN,
        ACRYLIC
    }

    private UUID id;
    private String name;

    @Singular("material")
    private List<Material> material;
}
