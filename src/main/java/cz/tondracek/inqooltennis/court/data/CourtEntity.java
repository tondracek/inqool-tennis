package cz.tondracek.inqooltennis.court.data;

import cz.tondracek.inqooltennis.common.baseentity.data.BaseDeletableEntity;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = true)
public class CourtEntity extends BaseDeletableEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "surface_type_id", nullable = false)
    private SurfaceTypeEntity surfaceType;

    public CourtEntity(UUID id, String name, SurfaceTypeEntity surfaceType, boolean deleted) {
        super(id, deleted);
        this.name = name;
        this.surfaceType = surfaceType;
    }
}
