package cz.tondracek.inqooltennis.user;

import cz.tondracek.inqooltennis.common.password.PasswordEncoder;
import cz.tondracek.inqooltennis.user.data.UserEntity;
import cz.tondracek.inqooltennis.user.dto.CreateUserDto;
import cz.tondracek.inqooltennis.user.dto.UpdateUserDto;
import cz.tondracek.inqooltennis.user.dto.UserDetailDto;
import cz.tondracek.inqooltennis.user.model.User;
import cz.tondracek.inqooltennis.user.model.UserRole;

import java.util.UUID;

public class UserSample {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_PASSWORD_HASH = PasswordEncoder.hash("user");

    public static final CreateUserDto CREATE_DTO = new CreateUserDto(
            "user@mail.com",
            "user",
            UserRole.USER
    );
    public static final User USER = new User(
            USER_ID,
            "user@mail.com",
            USER_PASSWORD_HASH,
            UserRole.USER,
            false
    );
    public static final User USER_DELETED = USER.withDeleted(true);
    public static final UserEntity USER_ENTITY = new UserEntity(
            USER_ID,
            "user@mail.com",
            USER_PASSWORD_HASH,
            UserRole.USER,
            false
    );
    public static final UserEntity USER_ENTITY_DELETED = new UserEntity(
            USER_ID,
            "user@mail.com",
            USER_PASSWORD_HASH,
            UserRole.USER,
            true
    );
    public static final UserDetailDto USER_DTO = new UserDetailDto(
            USER_ID,
            "user@mail.com",
            UserRole.USER,
            false
    );
    public static final User USER_DUPLICATE_EMAIL = new User(
            UUID.randomUUID(),
            "user@mail.com",
            PasswordEncoder.hash("duplicate_email"),
            UserRole.USER,
            false
    );

    public static final UpdateUserDto UPDATE_DTO = new UpdateUserDto(
            "updated_user@mail.com",
            "updated_user",
            UserRole.ADMIN
    );
    private static final String UPDATED_USER_PASSWORD_HASH = PasswordEncoder.hash("updated_user");
    public static final User UPDATED_USER = new User(
            USER_ID,
            "updated_user@mail.com",
            UPDATED_USER_PASSWORD_HASH,
            UserRole.ADMIN,
            false
    );
    public static final UserEntity UPDATED_USER_ENTITY = new UserEntity(
            USER_ID,
            "updated_user@mail.com",
            UPDATED_USER_PASSWORD_HASH,
            UserRole.ADMIN,
            false
    );
    public static final UserDetailDto UPDATED_USER_DTO = new UserDetailDto(
            USER_ID,
            "updated_user@mail.com",
            UserRole.ADMIN,
            false
    );

    private static final UUID ADMIN_ID = UUID.randomUUID();
    private static final String ADMIN_PASSWORD_HASH = PasswordEncoder.hash("admin");

    public static final User ADMIN = new User(
            ADMIN_ID,
            "admin@mail.com",
            ADMIN_PASSWORD_HASH,
            UserRole.ADMIN,
            false
    );
    public static final UserEntity ADMIN_ENTITY = new UserEntity(
            ADMIN_ID,
            "admin@mail.com",
            ADMIN_PASSWORD_HASH,
            UserRole.ADMIN,
            false
    );
    public static final UserDetailDto ADMIN_DTO = new UserDetailDto(
            ADMIN_ID,
            "admin@mail.com",
            UserRole.ADMIN,
            false
    );
}
