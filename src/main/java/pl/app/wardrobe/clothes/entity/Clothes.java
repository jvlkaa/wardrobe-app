package pl.app.wardrobe.clothes.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class Clothes {
    private UUID id;
    private String name;
    private List<Material> material;

}
