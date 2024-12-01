package pl.app.wardrobe;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VersionAndCreationDateAuditable {

    @Version
    private Long version;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "last_modified_date_time")
    private LocalDateTime lastModifiedDateTime;

    @PrePersist
    public void updateCreationDateTime() {
        creationDateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void updateLastModifiedDateTime() {
        lastModifiedDateTime = LocalDateTime.now();
    }
}


