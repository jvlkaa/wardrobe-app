package pl.app.wardrobe.clothes.model;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ClothesShortModel {
    private UUID id;
    private String name;
}
