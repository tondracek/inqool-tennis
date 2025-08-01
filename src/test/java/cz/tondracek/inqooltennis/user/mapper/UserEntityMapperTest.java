package cz.tondracek.inqooltennis.user.mapper;

import cz.tondracek.inqooltennis.user.data.UserEntity;
import cz.tondracek.inqooltennis.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static cz.tondracek.inqooltennis.user.UserSample.USER;
import static cz.tondracek.inqooltennis.user.UserSample.USER_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserEntityMapperTest {

    @Autowired
    UserEntityMapper userEntityMapper;

    @Test
    void toEntity() {
        UserEntity result = userEntityMapper.toEntity(USER);
        assertNotNull(result);
        assertEquals(USER.id(), result.getId());
        assertEquals(USER.email(), result.getEmail());
        assertEquals(USER.passwordHash(), result.getPasswordHash());
        assertEquals(USER.role(), result.getRole());
        assertEquals(USER.deleted(), result.isDeleted());
    }

    @Test
    void toModel() {
        User result = userEntityMapper.toModel(USER_ENTITY);
        assertNotNull(result);
        assertEquals(USER_ENTITY.getId(), result.id());
        assertEquals(USER_ENTITY.getEmail(), result.email());
        assertEquals(USER_ENTITY.getRole(), result.role());
        assertEquals(USER_ENTITY.getPasswordHash(), result.passwordHash());
        assertEquals(USER_ENTITY.isDeleted(), result.deleted());
    }
}
