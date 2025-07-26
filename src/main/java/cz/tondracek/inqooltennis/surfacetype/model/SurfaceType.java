package cz.tondracek.inqooltennis.surfacetype.model;

import cz.tondracek.inqooltennis.common.model.Price;
import cz.tondracek.inqooltennis.common.model.SoftDeletable;
import lombok.Value;

import java.util.UUID;

@Value
public class SurfaceType implements SoftDeletable<SurfaceType> {
    UUID id;
    String name;
    Price pricePerMinute;
    boolean deleted;

    @Override
    public SurfaceType withDeleted(boolean deletedValue) {
        return new SurfaceType(id, name, pricePerMinute, deletedValue);
    }
}
