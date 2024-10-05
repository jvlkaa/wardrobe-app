package pl.app.wardrobe.user.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.wardrobe.clothes.entity.Item;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class User {
    private UUID id;
    private String login;
    private String password;
    private LocalDate dateOfBirth;
    private Role role;
    private List<Item> wardrobe;
}
