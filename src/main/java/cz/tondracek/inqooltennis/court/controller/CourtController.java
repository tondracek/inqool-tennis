package cz.tondracek.inqooltennis.court.controller;

import cz.tondracek.inqooltennis.court.dto.CourtDetailDto;
import cz.tondracek.inqooltennis.court.dto.CreateCourtDto;
import cz.tondracek.inqooltennis.court.dto.UpdateCourtDto;
import cz.tondracek.inqooltennis.court.service.CourtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/courts")
public class CourtController {

    private final CourtService service;

    public CourtController(CourtService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CourtDetailDto> createCourt(
            @Valid @RequestBody CreateCourtDto dto
    ) {
        return ResponseEntity.ok(service.createCourt(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourtDetailDto> updateCourt(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCourtDto dto
    ) {
        return ResponseEntity.ok(service.updateCourt(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourt(
            @PathVariable UUID id
    ) {
        service.softDeleteCourt(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtDetailDto> getCourtById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(service.getCourtById(id));
    }

    @GetMapping
    public ResponseEntity<List<CourtDetailDto>> getAllCourts() {
        return ResponseEntity.ok(service.getAllCourts());
    }
}
