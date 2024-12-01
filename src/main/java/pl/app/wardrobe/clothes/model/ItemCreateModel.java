package pl.app.wardrobe.clothes.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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

    @NotBlank
    private String name;

    private Size size;

    @NotBlank
    private String color;

    @NotNull
    @Past
    private LocalDate purchaseDate;

    private ClothesShortModel clothesCategory;
}
