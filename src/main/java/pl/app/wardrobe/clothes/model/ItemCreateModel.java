package pl.app.wardrobe.clothes.model;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import pl.app.wardrobe.clothes.domain.DateHolder;
import pl.app.wardrobe.clothes.entity.Size;
import pl.app.wardrobe.clothes.validation.ValidItemDate;
import pl.app.wardrobe.clothes.validation.group.ItemModelGroup;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@ValidItemDate(groups = ItemModelGroup.class)
@Named
@ConversationScoped
public class ItemCreateModel implements DateHolder, Serializable {
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
