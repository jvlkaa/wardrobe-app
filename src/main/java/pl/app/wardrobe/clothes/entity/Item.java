package pl.app.wardrobe.clothes.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "items")
public class Item implements Serializable {
    @Id
    private UUID id;
    private String name;
    private Size size;
    private String color;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "clothes")
    private Clothes clothesCategory;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User owner;
}
