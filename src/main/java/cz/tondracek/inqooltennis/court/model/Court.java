package cz.tondracek.inqooltennis.court.model;

import cz.tondracek.inqooltennis.common.SoftDeletable;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import lombok.Value;

import java.util.UUID;

@Value
public class Court implements SoftDeletable<Court> {
    UUID id;
    String name;
    SurfaceType surfaceType;
    boolean deleted;

    @Override
    public Court withDeleted(boolean deletedValue) {
        return new Court(id, name, surfaceType, deletedValue);
    }
}
