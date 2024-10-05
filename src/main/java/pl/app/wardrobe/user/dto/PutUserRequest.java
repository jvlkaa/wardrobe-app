package pl.app.wardrobe.user.dto;

import lombok.*;

import java.time.LocalDate;

/* user creation, user input */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PutUserRequest {
    private String login;
    private String password;
    private LocalDate dateOfBirth;
    private String email;
}
