package cz.tondracek.inqooltennis.court;

import cz.tondracek.inqooltennis.court.data.CourtEntity;
import cz.tondracek.inqooltennis.court.dto.CourtDetailDto;
import cz.tondracek.inqooltennis.court.dto.CreateCourtDto;
import cz.tondracek.inqooltennis.court.dto.UpdateCourtDto;
import cz.tondracek.inqooltennis.court.model.Court;

import java.util.UUID;

import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_2;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_2_DTO;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_2_ENTITY;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_DTO;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_ENTITY;

public class CourtSample {

    private static final UUID SAMPLE_ID = UUID.randomUUID();

    public static final CreateCourtDto CREATE_DTO = new CreateCourtDto(
            "Court 1",
            SURFACE_TYPE.getId()
    );
    public static final Court COURT = new Court(
            SAMPLE_ID,
            "Court 1",
            SURFACE_TYPE,
            false
    );
    public static final Court COURT_DELETED = COURT.withDeleted(true);
    public static final CourtEntity COURT_ENTITY = new CourtEntity(
            SAMPLE_ID,
            "Court 1",
            SURFACE_TYPE_ENTITY,
            false
    );
    public static final CourtDetailDto COURT_DTO = new CourtDetailDto(
            SAMPLE_ID,
            "Court 1",
            SURFACE_TYPE_DTO,
            false
    );

    public static final UpdateCourtDto UPDATE_DTO = new UpdateCourtDto(
            "Court 2",
            SURFACE_TYPE_2.getId()
    );
    public static final Court UPDATED_COURT = new Court(
            SAMPLE_ID,
            "Court 2",
            SURFACE_TYPE_2,
            false
    );
    public static final CourtEntity UPDATED_COURT_ENTITY = new CourtEntity(
            SAMPLE_ID,
            "Court 2",
            SURFACE_TYPE_2_ENTITY,
            false
    );
    public static final CourtDetailDto UPDATED_COURT_DTO = new CourtDetailDto(
            SAMPLE_ID,
            "Court 2",
            SURFACE_TYPE_2_DTO,
            false
    );

    private static final UUID COURT_2_ID = UUID.randomUUID();
    public static final Court COURT_2 = new Court(
            COURT_2_ID,
            "Court 2",
            SURFACE_TYPE_2,
            false
    );
    public static final CourtEntity COURT_2_ENTITY = new CourtEntity(
            COURT_2_ID,
            "Court 2",
            SURFACE_TYPE_2_ENTITY,
            false
    );
    public static final CourtDetailDto COURT_2_DTO = new CourtDetailDto(
            COURT_2_ID,
            "Court 2",
            SURFACE_TYPE_2_DTO,
            false
    );
}
