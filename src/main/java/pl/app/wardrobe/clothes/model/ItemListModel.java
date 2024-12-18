package pl.app.wardrobe.clothes.model;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ItemListModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Item {
        private UUID id;
        private String name;
        private LocalDateTime lastModifiedDateTime;
        private LocalDateTime creationDateTime;
        private Long version;
    }

    @Singular("itemList")
    private List<Item> itemList;

}
