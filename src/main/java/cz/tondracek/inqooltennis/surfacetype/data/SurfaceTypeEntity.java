package cz.tondracek.inqooltennis.surfacetype.data;

import cz.tondracek.inqooltennis.common.baseentity.data.BaseDeletableEntity;
import cz.tondracek.inqooltennis.common.price.data.PriceEmbeddable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SurfaceTypeEntity extends BaseDeletableEntity {

    @Column(nullable = false)
    private String name;

    @Embedded
    private PriceEmbeddable pricePerMinute;

    public SurfaceTypeEntity(UUID id, String name, PriceEmbeddable pricePerMinute, boolean deleted) {
        super(id, deleted);
        this.name = name;
        this.pricePerMinute = pricePerMinute;
    }
}
