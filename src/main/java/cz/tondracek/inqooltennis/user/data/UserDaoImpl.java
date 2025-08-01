package cz.tondracek.inqooltennis.user.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(UserEntity userEntity) {
        entityManager.persist(userEntity);
    }

    @Override
    public void update(UserEntity userEntity) {
        entityManager.merge(userEntity);
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, id));
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        var query = entityManager
                .createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                .setParameter("email", email);

        return query.getResultStream().findFirst();
    }

    @Override
    public List<UserEntity> findAllActive() {
        return entityManager
                .createQuery("SELECT u FROM UserEntity u WHERE u.deleted = false", UserEntity.class)
                .getResultList();
    }
}
