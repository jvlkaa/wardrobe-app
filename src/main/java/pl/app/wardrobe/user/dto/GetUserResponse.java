package pl.app.wardrobe.user.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

/* fields of User to display */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GetUserResponse {
    private UUID id;
    private String login;
    private String email;
    private LocalDate dateOfBirth;
}
