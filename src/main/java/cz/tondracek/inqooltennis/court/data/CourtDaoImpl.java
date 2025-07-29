package cz.tondracek.inqooltennis.court.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Repository
public class CourtDaoImpl implements CourtDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(CourtEntity entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(CourtEntity entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<CourtEntity> findById(UUID id) {
        CourtEntity entity = entityManager.find(CourtEntity.class, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<CourtEntity> findActiveById(UUID id) {
        try {
            CourtEntity entity = entityManager
                    .createQuery("SELECT c FROM CourtEntity c WHERE c.id = :id AND c.deleted = false", CourtEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(entity);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CourtEntity> findAllActive() {
        return entityManager
                .createQuery("SELECT c FROM CourtEntity c WHERE c.deleted = false", CourtEntity.class)
                .getResultList();
    }
}
