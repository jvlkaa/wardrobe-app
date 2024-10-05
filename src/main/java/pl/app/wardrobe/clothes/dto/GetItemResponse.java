package pl.app.wardrobe.clothes.dto;

import lombok.*;
import pl.app.wardrobe.clothes.entity.Size;
import java.time.LocalDate;
import java.util.UUID;

/* item response with category name and without photo*/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GetItemResponse {
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @EqualsAndHashCode
    public static class Clothes{
        private UUID id;
        private String name;
    }

    private UUID id;
    private String name;
    private Size size;
    private String color;
    private LocalDate purchaseDate;
    private Clothes clothesCategory;
}
