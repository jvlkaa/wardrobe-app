package pl.app.wardrobe.clothes.model;
import lombok.*;
import pl.app.wardrobe.user.model.UserModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ItemEditModel {
    private String name;
    private UserModel owner;
    private LocalDateTime lastModifiedDateTime;
    private LocalDateTime creationDateTime;
}
