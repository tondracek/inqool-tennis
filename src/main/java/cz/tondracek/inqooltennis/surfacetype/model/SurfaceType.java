package cz.tondracek.inqooltennis.surfacetype.model;

import cz.tondracek.inqooltennis.common.SoftDeletable;
import cz.tondracek.inqooltennis.common.price.model.Price;
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
