package cz.tondracek.inqooltennis.reservation.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationDao {

    void save(ReservationEntity entity);

    void update(ReservationEntity entity);

    Optional<ReservationEntity> findById(UUID id);

    List<ReservationEntity> findAllActive();

    List<ReservationEntity> findActiveByCourtId(UUID courtId);

    List<ReservationEntity> findActiveByPhoneNumber(String phoneNumber);

    List<ReservationEntity> findActiveFutureByPhoneNumber(String phoneNumber);
}
