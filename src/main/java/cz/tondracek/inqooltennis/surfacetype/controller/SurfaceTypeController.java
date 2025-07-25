package cz.tondracek.inqooltennis.surfacetype.controller;

import cz.tondracek.inqooltennis.surfacetype.dto.CreateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.dto.UpdateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.service.SurfaceTypeService;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/surface-types")
public class SurfaceTypeController {

    private final SurfaceTypeService service;

    public SurfaceTypeController(SurfaceTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SurfaceTypeDetailDto> createSurfaceType(
            @RequestBody CreateSurfaceTypeDto dto) {
        return ResponseEntity.ok(service.createSurfaceType(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurfaceTypeDetailDto> updateSurfaceType(
            @PathVariable UUID id,
            @RequestBody UpdateSurfaceTypeDto dto) {
        return ResponseEntity.ok(service.updateSurfaceType(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<SurfaceTypeDetailDto>> getAllSurfaceTypes() {
        return ResponseEntity.ok(service.getAllSurfaceTypes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSurfaceType(@PathVariable UUID id) {
        return ResponseEntity.ok(service.deleteSurfaceType(id));
    }
}
