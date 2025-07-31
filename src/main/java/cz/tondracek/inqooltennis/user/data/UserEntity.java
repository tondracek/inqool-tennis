package cz.tondracek.inqooltennis.user.data;

import cz.tondracek.inqooltennis.common.baseentity.data.BaseDeletableEntity;
import cz.tondracek.inqooltennis.user.model.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseDeletableEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserEntity(UUID id, String email, String passwordHash, UserRole role, boolean deleted) {
        super(id, deleted);
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }
}
