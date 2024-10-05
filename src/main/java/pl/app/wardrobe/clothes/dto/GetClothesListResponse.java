package pl.app.wardrobe.clothes.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

/* list of categories name */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GetClothesListResponse {
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @EqualsAndHashCode
    public static class Clothes{
        private UUID id;
        private String name;
    }

    private List<Clothes> clothes;
}
