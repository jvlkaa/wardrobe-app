package pl.app.wardrobe.user.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.wardrobe.clothes.entity.Item;

import java.io.Serializable;
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
public class User implements Serializable {
    private UUID id;
    private String login;
    private String password;
    private LocalDate dateOfBirth;
    private String email;
    private Role role;
    private String avatar;
}
