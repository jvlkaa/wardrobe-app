package pl.app.wardrobe.clothes.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.wardrobe.user.entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class Item implements Serializable {
    private UUID id;
    private String name;
    private Size size;
    private String color;
    private LocalDate purchaseDate;
    private Clothes clothesCategory;
    private User owner;
}
