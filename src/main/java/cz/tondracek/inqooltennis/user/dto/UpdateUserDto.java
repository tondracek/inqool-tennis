package cz.tondracek.inqooltennis.user.dto;

import cz.tondracek.inqooltennis.user.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserDto(
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotNull
        UserRole role
) {
}
