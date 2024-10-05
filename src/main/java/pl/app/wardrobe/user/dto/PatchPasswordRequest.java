package pl.app.wardrobe.user.dto;

import lombok.*;

/* update password */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PatchPasswordRequest {
    private String password;

}

