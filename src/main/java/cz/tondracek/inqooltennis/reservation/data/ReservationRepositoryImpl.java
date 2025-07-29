package cz.tondracek.inqooltennis.reservation.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.reservation.mapper.ReservationEntityMapper;
import cz.tondracek.inqooltennis.reservation.model.Reservation;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationDao dao;
    private final ReservationEntityMapper mapper;

    public ReservationRepositoryImpl(
            ReservationDao dao,
            ReservationEntityMapper mapper
    ) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Reservation create(Reservation reservation) {
        ReservationEntity entity = mapper.toEntity(reservation);

        dao.save(entity);
        return reservation;
    }

    @Override
    public Reservation update(Reservation reservation) {
        ReservationEntity entity = mapper.toEntity(reservation);

        dao.update(entity);
        return reservation;
    }

    @NotNull
    @Override
    public Reservation findById(UUID id) {
        ReservationEntity entity = dao.findById(id).orElseThrow(NotFoundException::new);
        return mapper.toModel(entity);
    }

    @Override
    public List<Reservation> findAllActive() {
        return dao.findAllActive().stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public List<Reservation> findActiveByCourtId(UUID courtId) {
        return dao.findActiveByCourtId(courtId).stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public List<Reservation> findActiveByPhoneNumber(String phoneNumber) {
        return dao.findActiveByPhoneNumber(phoneNumber).stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public List<Reservation> findActiveFutureByPhoneNumber(String phoneNumber) {
        return dao.findActiveFutureByPhoneNumber(phoneNumber).stream()
                .map(mapper::toModel)
                .toList();
    }
}
