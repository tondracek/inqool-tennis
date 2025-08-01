package cz.tondracek.inqooltennis.user.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.user.mapper.UserEntityMapper;
import cz.tondracek.inqooltennis.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static cz.tondracek.inqooltennis.user.UserSample.ADMIN;
import static cz.tondracek.inqooltennis.user.UserSample.ADMIN_ENTITY;
import static cz.tondracek.inqooltennis.user.UserSample.UPDATED_USER;
import static cz.tondracek.inqooltennis.user.UserSample.UPDATED_USER_ENTITY;
import static cz.tondracek.inqooltennis.user.UserSample.USER;
import static cz.tondracek.inqooltennis.user.UserSample.USER_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserDao dao;

    @Mock
    private UserEntityMapper mapper;

    @InjectMocks
    private UserRepositoryImpl repository;

    @Test
    void create() {
        when(mapper.toEntity(USER)).thenReturn(USER_ENTITY);
        User result = repository.create(USER);

        assertEquals(USER, result);
        verify(dao).save(USER_ENTITY);
    }

    @Test
    void update() {
        when(mapper.toEntity(UPDATED_USER)).thenReturn(UPDATED_USER_ENTITY);

        User result = repository.update(UPDATED_USER);

        assertEquals(UPDATED_USER, result);
        verify(dao).update(UPDATED_USER_ENTITY);
    }

    @Test
    void findById() {
        when(dao.findById(USER.id())).thenReturn(Optional.of(USER_ENTITY));
        when(mapper.toModel(USER_ENTITY)).thenReturn(USER);

        User result = repository.findById(USER.id());

        assertEquals(USER, result);
    }

    @Test
    void findById_shouldThrowOnMissing() {
        when(dao.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> repository.findById(UUID.randomUUID()));
    }

    @Test
    void findByEmail() {
        when(dao.findByEmail(USER.email())).thenReturn(Optional.of(USER_ENTITY));
        when(mapper.toModel(USER_ENTITY)).thenReturn(USER);

        User result = repository.findByEmail(USER.email());

        assertEquals(USER, result);
    }

    @Test
    void findByEmail_shouldThrowOnMissing() {
        when(dao.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> repository.findByEmail("missing@email.com"));
    }

    @Test
    void findAllActive() {
        when(dao.findAllActive()).thenReturn(List.of(USER_ENTITY, ADMIN_ENTITY));
        when(mapper.toModel(USER_ENTITY)).thenReturn(USER);
        when(mapper.toModel(ADMIN_ENTITY)).thenReturn(ADMIN);

        List<User> result = repository.findAllActive();

        assertEquals(2, result.size());
        assertTrue(result.contains(USER));
        assertTrue(result.contains(ADMIN));
    }
}
