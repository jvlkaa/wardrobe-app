package pl.app.wardrobe.clothes.dto;

import lombok.*;
import pl.app.wardrobe.clothes.entity.Size;

import java.time.LocalDate;
import java.util.UUID;

/* input user while adding new item to wardrobe */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PutItemRequest {

    private String name;
    private Size size;
    private String color;
    private LocalDate purchaseDate;
}
