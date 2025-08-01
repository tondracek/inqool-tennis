package cz.tondracek.inqooltennis.user.dto;

import cz.tondracek.inqooltennis.user.model.UserRole;

import java.util.UUID;

public record UserDetailDto(
        UUID id,
        String email,
        UserRole role,
        boolean deleted
) {
}
