package cz.tondracek.inqooltennis.user.model;

import cz.tondracek.inqooltennis.common.SoftDeletable;

import java.util.UUID;

public record User(
        UUID id,
        String email,
        String passwordHash,
        UserRole role,
        boolean deleted
) implements SoftDeletable<User> {

    @Override
    public User withDeleted(boolean deletedValue) {
        return new User(id, email, passwordHash, role, deletedValue);
    }
}
