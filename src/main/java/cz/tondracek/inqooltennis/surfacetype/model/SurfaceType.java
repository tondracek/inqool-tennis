package cz.tondracek.inqooltennis.surfacetype.model;

import cz.tondracek.inqooltennis.common.model.Price;
import lombok.Data;

import java.util.UUID;

@Data
public class SurfaceType {
    private UUID id;
    private String name;
    private Price pricePerMinute;
    private boolean deleted;
}
