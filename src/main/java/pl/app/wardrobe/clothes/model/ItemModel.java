package pl.app.wardrobe.clothes.model;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ItemModel {
    private String name;
    private String size;
    private String color;
    private LocalDate purchaseDate;
    private String clothesCategory;
}
