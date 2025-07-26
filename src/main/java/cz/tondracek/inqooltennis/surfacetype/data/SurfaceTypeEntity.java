package cz.tondracek.inqooltennis.surfacetype.data;

import cz.tondracek.inqooltennis.common.data.PriceEmbeddable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurfaceTypeEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private PriceEmbeddable pricePerMinute;

    @Column(nullable = false)
    private boolean deleted;
}
