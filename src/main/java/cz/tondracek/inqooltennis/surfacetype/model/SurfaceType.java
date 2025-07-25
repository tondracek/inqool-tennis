package cz.tondracek.inqooltennis.surfacetype.model;

import cz.tondracek.inqooltennis.common.model.Price;
import lombok.Value;

import java.util.UUID;

@Value
public class SurfaceType {
    UUID id;
    String name;
    Price pricePerMinute;
    boolean deleted;
}
