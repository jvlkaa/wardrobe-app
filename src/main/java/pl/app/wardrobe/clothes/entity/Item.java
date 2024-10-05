package pl.app.wardrobe.clothes.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class Item {
    private UUID id;
    private String name;
    private Size size;
    private String color;
    private LocalDate purchaseDate;
    private Clothes clothesCategory;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private byte[] photo;
}
