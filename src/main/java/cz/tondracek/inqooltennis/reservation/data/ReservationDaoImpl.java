package cz.tondracek.inqooltennis.reservation.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Repository
public class ReservationDaoImpl implements ReservationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(ReservationEntity entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(ReservationEntity entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<ReservationEntity> findById(UUID id) {
        ReservationEntity entity = entityManager.find(ReservationEntity.class, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public List<ReservationEntity> findAllActive() {
        String query = "SELECT r FROM ReservationEntity r WHERE r.deleted = false ORDER BY r.createdAt DESC";
        return entityManager
                .createQuery(query, ReservationEntity.class)
                .getResultList();
    }

    @Override
    public List<ReservationEntity> findActiveByCourtId(UUID courtId) {
        String query = "SELECT r FROM ReservationEntity r WHERE r.court.id = :courtId AND r.deleted = false ORDER BY r.createdAt DESC";
        return entityManager
                .createQuery(query, ReservationEntity.class)
                .setParameter("courtId", courtId)
                .getResultList();
    }

    @Override
    public List<ReservationEntity> findActiveByPhoneNumber(String phoneNumber) {
        String query = "SELECT r FROM ReservationEntity r WHERE r.customer.phoneNumber = :phoneNumber AND r.deleted = false ORDER BY r.createdAt DESC";
        return entityManager
                .createQuery(query, ReservationEntity.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
    }

    @Override
    public List<ReservationEntity> findActiveFutureByPhoneNumber(String phoneNumber) {
        String query = "SELECT r FROM ReservationEntity r WHERE r.customer.phoneNumber = :phoneNumber AND r.deleted = false AND r.startTime > CURRENT_TIMESTAMP ORDER BY r.startTime ASC";
        return entityManager
                .createQuery(query, ReservationEntity.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
    }
}
