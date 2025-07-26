package cz.tondracek.inqooltennis.court.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateCourtDto {
    @NotBlank
    String name;
    @NotNull
    UUID surfaceTypeId;
}