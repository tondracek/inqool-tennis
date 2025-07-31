package cz.tondracek.inqooltennis.user.mapper;

import cz.tondracek.inqooltennis.user.dto.UserDetailDto;
import cz.tondracek.inqooltennis.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static cz.tondracek.inqooltennis.user.UserSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.user.UserSample.UPDATED_USER;
import static cz.tondracek.inqooltennis.user.UserSample.UPDATE_DTO;
import static cz.tondracek.inqooltennis.user.UserSample.USER;
import static cz.tondracek.inqooltennis.user.UserSample.USER_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void createToUser() {
        User user = userMapper.toUser(CREATE_DTO, USER.id(), USER.passwordHash());

        assertNotNull(user);
        assertEquals(USER, user);
    }

    @Test
    void updateToUser() {
        User updated = userMapper.toUser(UPDATE_DTO, UPDATED_USER.passwordHash(), USER);

        assertNotNull(updated);
        assertEquals(UPDATED_USER, updated);
    }

    @Test
    void toDetailDto() {
        UserDetailDto dto = userMapper.toDetailDto(USER);

        assertNotNull(dto);
        assertEquals(USER_DTO, dto);
    }
}
