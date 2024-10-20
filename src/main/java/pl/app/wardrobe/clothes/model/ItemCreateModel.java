package pl.app.wardrobe.clothes.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.app.wardrobe.clothes.entity.Size;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ItemCreateModel {
    private UUID id;
    private String name;
    private Size size;
    private String color;
    private LocalDate purchaseDate;
    private ClothesShortModel clothesCategory;
}
