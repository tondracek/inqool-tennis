package cz.tondracek.inqooltennis.user.service;

import cz.tondracek.inqooltennis.core.exception.BadRequestException;
import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.user.data.UserRepository;
import cz.tondracek.inqooltennis.user.dto.CreateUserDto;
import cz.tondracek.inqooltennis.user.dto.UpdateUserDto;
import cz.tondracek.inqooltennis.user.dto.UserDetailDto;
import cz.tondracek.inqooltennis.user.mapper.UserMapper;
import cz.tondracek.inqooltennis.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static cz.tondracek.inqooltennis.user.UserSample.ADMIN;
import static cz.tondracek.inqooltennis.user.UserSample.ADMIN_DTO;
import static cz.tondracek.inqooltennis.user.UserSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.user.UserSample.UPDATED_USER;
import static cz.tondracek.inqooltennis.user.UserSample.UPDATED_USER_DTO;
import static cz.tondracek.inqooltennis.user.UserSample.UPDATE_DTO;
import static cz.tondracek.inqooltennis.user.UserSample.USER;
import static cz.tondracek.inqooltennis.user.UserSample.USER_DELETED;
import static cz.tondracek.inqooltennis.user.UserSample.USER_DTO;
import static cz.tondracek.inqooltennis.user.UserSample.USER_DUPLICATE_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @Mock
    UserMapper mapper;

    @InjectMocks
    UserServiceImpl service;

    @Test
    void createUser() {
        when(mapper.toUser(eq(CREATE_DTO), any(UUID.class), any(String.class))).thenReturn(USER);
        when(repository.findByEmail(USER.email())).thenThrow(NotFoundException.class);
        when(repository.create(USER)).thenReturn(USER);
        when(mapper.toDetailDto(USER)).thenReturn(USER_DTO);

        UserDetailDto result = service.createUser(CREATE_DTO);

        assertEquals(USER_DTO, result);
        verify(repository).create(USER);
    }

    @Test
    void createUser_shouldThrowOnInvalidEmail() {
        CreateUserDto dto = new CreateUserDto("invalid", "pass", USER.role());

        assertThrows(BadRequestException.class, () -> service.createUser(dto));
    }

    @Test
    void createUser_shouldThrowOnExistingEmail() {
        when(mapper.toUser(eq(CREATE_DTO), any(UUID.class), any())).thenReturn(USER);
        when(repository.findByEmail(USER_DUPLICATE_EMAIL.email())).thenReturn(USER_DUPLICATE_EMAIL);

        assertThrows(ConflictException.class, () -> service.createUser(CREATE_DTO));
    }

    @Test
    void updateUser() {
        when(repository.findById(USER.id())).thenReturn(USER);
        when(mapper.toUser(eq(UPDATE_DTO), any(), eq(USER))).thenReturn(UPDATED_USER);
        when(repository.update(UPDATED_USER)).thenReturn(UPDATED_USER);
        when(mapper.toDetailDto(UPDATED_USER)).thenReturn(UPDATED_USER_DTO);

        UserDetailDto result = service.updateUser(USER.id(), UPDATE_DTO);

        assertEquals(UPDATED_USER_DTO, result);
    }

    @Test
    void updateUser_shouldSucceedOnSameEmail() {
        when(repository.findById(USER.id())).thenReturn(USER);
        when(mapper.toUser(eq(UPDATE_DTO), any(), eq(USER))).thenReturn(UPDATED_USER);
        when(repository.findByEmail(UPDATED_USER.email())).thenReturn(UPDATED_USER);
        when(repository.update(UPDATED_USER)).thenReturn(UPDATED_USER);
        when(mapper.toDetailDto(UPDATED_USER)).thenReturn(UPDATED_USER_DTO);

        UserDetailDto result = service.updateUser(USER.id(), UPDATE_DTO);

        assertEquals(UPDATED_USER_DTO, result);
    }

    @Test
    void updateUser_shouldThrowOnInvalidEmail() {
        UpdateUserDto dto = new UpdateUserDto("invalid", "pass", USER.role());

        assertThrows(BadRequestException.class, () -> service.updateUser(USER.id(), dto));
    }

    @Test
    void softDeleteUser() {
        when(repository.findById(USER.id())).thenReturn(USER);
        when(repository.update(USER_DELETED)).thenReturn(USER_DELETED);

        service.softDeleteUser(USER.id());

        verify(repository).update(USER_DELETED);
    }

    @Test
    void findUserById() {
        when(repository.findById(USER.id())).thenReturn(USER);
        when(mapper.toDetailDto(USER)).thenReturn(USER_DTO);

        UserDetailDto result = service.findUserById(USER.id().toString());

        assertEquals(USER_DTO, result);
    }

    @Test
    void findUserByEmail() {
        when(repository.findByEmail(USER.email())).thenReturn(USER);
        when(mapper.toDetailDto(USER)).thenReturn(USER_DTO);

        UserDetailDto result = service.findUserByEmail(USER.email());

        assertEquals(USER_DTO, result);
    }

    @Test
    void findAllActiveUsers() {
        List<User> users = List.of(USER, ADMIN);
        when(repository.findAllActive()).thenReturn(users);
        when(mapper.toDetailDto(USER)).thenReturn(USER_DTO);
        when(mapper.toDetailDto(ADMIN)).thenReturn(ADMIN_DTO);

        List<UserDetailDto> result = service.findAllActiveUsers();

        assertEquals(List.of(USER_DTO, ADMIN_DTO), result);
    }
}
