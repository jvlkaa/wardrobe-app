package pl.app.wardrobe.user.dto;

import lombok.*;

import java.time.LocalDate;

/* fields of the user that can be changed */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PatchUserRequest {
    private LocalDate dateOfBirth;
    private String email;
}
