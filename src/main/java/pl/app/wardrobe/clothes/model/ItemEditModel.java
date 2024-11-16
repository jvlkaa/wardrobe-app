package pl.app.wardrobe.clothes.model;
import lombok.*;
import pl.app.wardrobe.user.model.UserModel;

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
}
