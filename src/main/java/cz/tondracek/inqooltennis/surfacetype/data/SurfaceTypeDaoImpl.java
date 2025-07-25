package cz.tondracek.inqooltennis.surfacetype.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class SurfaceTypeDaoImpl implements SurfaceTypeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(SurfaceTypeEntity entity) {
        entityManager.persist(entity);
    }

    @Override
    public Optional<SurfaceTypeEntity> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(SurfaceTypeEntity.class, id));
    }

    @Override
    public List<SurfaceTypeEntity> findAll() {
        return entityManager
                .createQuery("SELECT s FROM SurfaceTypeEntity s", SurfaceTypeEntity.class)
                .getResultList();
    }

    @Override
    public void update(SurfaceTypeEntity entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(SurfaceTypeEntity entity) {
        entityManager.remove(entityManager.contains(entity)
                ? entity
                : entityManager.merge(entity));
    }
}
