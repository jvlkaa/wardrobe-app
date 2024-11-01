package pl.app.wardrobe.clothes.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "clothes")
public class Clothes implements Serializable {
    @Id
    private UUID id;
    private String name;

    @ElementCollection //przechowywanie listy enumów w osobnej tabeli, ponieważ List<Material> nie jest encją ani typem wbudowanym
    @Enumerated(EnumType.STRING) //zapisuje wartość enuma jako string
    private List<Material> material;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "clothesCategory", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Item> items;
}
