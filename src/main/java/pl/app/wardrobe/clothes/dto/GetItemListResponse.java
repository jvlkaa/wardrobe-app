package pl.app.wardrobe.clothes.dto;
import lombok.*;
import java.util.List;
import java.util.UUID;

/* list of items name */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GetItemListResponse {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @EqualsAndHashCode
    public static class Item {
        private UUID id;
        private String name;

    }
    @Singular("item")
    private List<Item> items;
}
