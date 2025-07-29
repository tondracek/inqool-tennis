package cz.tondracek.inqooltennis.surfacetype;

import cz.tondracek.inqooltennis.common.price.PriceSample;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeEntity;
import cz.tondracek.inqooltennis.surfacetype.dto.CreateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.dto.UpdateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;

import java.util.UUID;

public class SurfaceTypeSample {

    private static final UUID SAMPLE_ID = UUID.randomUUID();

    public static final CreateSurfaceTypeDto CREATE_DTO = new CreateSurfaceTypeDto(
            "Clay",
            PriceSample.EUR_200_DTO
    );

    public static final SurfaceType SURFACE_TYPE = new SurfaceType(
            SAMPLE_ID,
            "Clay",
            PriceSample.EUR_200,
            false
    );
    public static final SurfaceType SURFACE_TYPE_DELETED = SURFACE_TYPE.withDeleted(true);
    public static final SurfaceTypeEntity SURFACE_TYPE_ENTITY = new SurfaceTypeEntity(
            SAMPLE_ID,
            "Clay",
            PriceSample.EUR_200_EMBEDDABLE,
            false
    );
    public static final SurfaceTypeDetailDto SURFACE_TYPE_DTO = new SurfaceTypeDetailDto(
            SAMPLE_ID,
            "Clay",
            PriceSample.EUR_200_DTO,
            false
    );

    public static final UpdateSurfaceTypeDto UPDATE_DTO = new UpdateSurfaceTypeDto(
            "Grass",
            PriceSample.CZK_100_DTO
    );
    public static final SurfaceType UPDATED_SURFACE_TYPE = new SurfaceType(
            SAMPLE_ID,
            "Grass",
            PriceSample.CZK_100,
            false
    );
    public static final SurfaceTypeEntity UPDATED_SURFACE_TYPE_ENTITY = new SurfaceTypeEntity(
            SAMPLE_ID,
            "Grass",
            PriceSample.CZK_100_EMBEDDABLE,
            false
    );
    public static final SurfaceTypeDetailDto UPDATED_SURFACE_TYPE_DTO = new SurfaceTypeDetailDto(
            SAMPLE_ID,
            "Grass",
            PriceSample.CZK_100_DTO,
            false
    );

    private static final UUID SURFACE_TYPE_2_ID = UUID.randomUUID();
    public static final SurfaceType SURFACE_TYPE_2 = new SurfaceType(
            SURFACE_TYPE_2_ID,
            "Grass",
            PriceSample.EUR_100,
            false
    );
    public static final SurfaceTypeEntity SURFACE_TYPE_2_ENTITY = new SurfaceTypeEntity(
            SURFACE_TYPE_2_ID,
            "Grass",
            PriceSample.EUR_100_EMBEDDABLE,
            false
    );
    public static final SurfaceTypeDetailDto SURFACE_TYPE_2_DTO = new SurfaceTypeDetailDto(
            SURFACE_TYPE_2_ID,
            "Grass",
            PriceSample.EUR_100_DTO,
            false
    );
}
