package pl.app.wardrobe.clothes.dto;
import lombok.*;

/* input for the user - fields that can be updated (only name) */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PatchItemRequest {
    private String name;
    private Long version;
}
