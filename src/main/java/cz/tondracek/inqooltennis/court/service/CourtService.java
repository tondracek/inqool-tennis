package cz.tondracek.inqooltennis.court.service;

import cz.tondracek.inqooltennis.court.dto.CourtDetailDto;
import cz.tondracek.inqooltennis.court.dto.CreateCourtDto;
import cz.tondracek.inqooltennis.court.dto.UpdateCourtDto;

import java.util.List;
import java.util.UUID;

public interface CourtService {

    CourtDetailDto createCourt(CreateCourtDto dto);

    CourtDetailDto updateCourt(UUID id, UpdateCourtDto dto);

    void softDeleteCourt(UUID id);

    CourtDetailDto getCourtById(UUID id);

    List<CourtDetailDto> getAllCourts();
}
