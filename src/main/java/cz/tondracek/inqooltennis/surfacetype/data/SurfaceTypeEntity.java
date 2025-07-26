package cz.tondracek.inqooltennis.surfacetype.data;

import cz.tondracek.inqooltennis.common.price.data.PriceEmbeddable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
