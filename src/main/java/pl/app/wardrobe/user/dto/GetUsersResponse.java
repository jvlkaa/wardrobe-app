package pl.app.wardrobe.user.dto;

import lombok.*;
import java.util.List;
import java.util.UUID;

/* list of the users */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GetUsersResponse {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @EqualsAndHashCode
    public static class User{
        private UUID id;
        private String login;
    }

    @Singular
    private List<User> users;
}
