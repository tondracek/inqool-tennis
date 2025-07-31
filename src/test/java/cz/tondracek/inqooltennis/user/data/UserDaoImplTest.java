package cz.tondracek.inqooltennis.user.data;

import cz.tondracek.inqooltennis.user.UserSample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(UserDaoImpl.class)
class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save() {
        userDao.save(UserSample.USER_ENTITY);

        UserEntity result = entityManager.find(UserEntity.class, UserSample.USER_ENTITY.getId());

        assertNotNull(result);
        assertEquals(UserSample.USER_ENTITY.getEmail(), result.getEmail());
        assertEquals(UserSample.USER_ENTITY.getRole(), result.getRole());
        assertFalse(result.isDeleted());
    }

    @Test
    void update() {
        entityManager.persist(UserSample.USER_ENTITY);

        userDao.update(UserSample.UPDATED_USER_ENTITY);

        UserEntity result = entityManager.find(UserEntity.class, UserSample.USER_ENTITY.getId());

        assertNotNull(result);
        assertEquals(UserSample.UPDATED_USER_ENTITY.getEmail(), result.getEmail());
        assertEquals(UserSample.UPDATED_USER_ENTITY.getRole(), result.getRole());
    }

    @Test
    void findById_found() {
        entityManager.persist(UserSample.USER_ENTITY);

        Optional<UserEntity> result = userDao.findById(UserSample.USER_ENTITY.getId());

        assertTrue(result.isPresent());
        assertEquals(UserSample.USER_ENTITY.getEmail(), result.get().getEmail());
    }

    @Test
    void findById_notFound() {
        Optional<UserEntity> result = userDao.findById(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void findByEmail_found() {
        entityManager.persist(UserSample.USER_ENTITY);

        Optional<UserEntity> result = userDao.findByEmail(UserSample.USER_ENTITY.getEmail());

        assertTrue(result.isPresent());
        assertEquals(UserSample.USER_ENTITY.getId(), result.get().getId());
    }

    @Test
    void findByEmail_notFound() {
        Optional<UserEntity> result = userDao.findByEmail("nonexistent@example.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllActive() {
        entityManager.persist(UserSample.USER_ENTITY_DELETED);
        entityManager.persist(UserSample.ADMIN_ENTITY);

        List<UserEntity> results = userDao.findAllActive();

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(u -> u.getId().equals(UserSample.ADMIN_ENTITY.getId())));
    }
}
