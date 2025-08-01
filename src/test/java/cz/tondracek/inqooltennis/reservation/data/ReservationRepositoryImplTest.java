package cz.tondracek.inqooltennis.reservation.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.reservation.mapper.ReservationEntityMapper;
import cz.tondracek.inqooltennis.reservation.model.Reservation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_2000;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_2000_ENTITY;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_ENTITY;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.UPDATED_RESERVATION;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.UPDATED_RESERVATION_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationRepositoryImplTest {

    @Mock
    private ReservationDao dao;

    @Mock
    private ReservationEntityMapper mapper;

    @InjectMocks
    private ReservationRepositoryImpl repository;

    @Test
    void create() {
        when(mapper.toEntity(RESERVATION)).thenReturn(RESERVATION_ENTITY);

        Reservation result = repository.create(RESERVATION);

        assertEquals(RESERVATION, result);
        Mockito.verify(dao).save(RESERVATION_ENTITY);
    }

    @Test
    void update() {
        when(mapper.toEntity(UPDATED_RESERVATION)).thenReturn(UPDATED_RESERVATION_ENTITY);

        Reservation result = repository.update(UPDATED_RESERVATION);

        assertEquals(UPDATED_RESERVATION, result);
        Mockito.verify(dao).update(UPDATED_RESERVATION_ENTITY);
    }

    @Test
    void findById_shouldReturnReservation() {
        when(dao.findById(RESERVATION.getId())).thenReturn(Optional.of(RESERVATION_ENTITY));
        when(mapper.toModel(RESERVATION_ENTITY)).thenReturn(RESERVATION);

        Reservation result = repository.findById(RESERVATION.getId());

        assertEquals(RESERVATION, result);
    }

    @Test
    void findById_shouldThrowIfNotFound() {
        when(dao.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> repository.findById(UUID.randomUUID()));
    }

    @Test
    void findAllActive() {
        when(dao.findAllActive()).thenReturn(List.of(RESERVATION_ENTITY, RESERVATION_2000_ENTITY));
        when(mapper.toModel(RESERVATION_ENTITY)).thenReturn(RESERVATION);
        when(mapper.toModel(RESERVATION_2000_ENTITY)).thenReturn(RESERVATION_2000);

        List<Reservation> results = repository.findAllActive();

        assertEquals(List.of(RESERVATION, RESERVATION_2000), results);
    }

    @Test
    void findActiveByCourtId() {
        when(dao.findActiveByCourtId(RESERVATION_2000.getCourt().getId()))
                .thenReturn(List.of(RESERVATION_2000_ENTITY));
        when(mapper.toModel(RESERVATION_2000_ENTITY)).thenReturn(RESERVATION_2000);

        List<Reservation> results = repository.findActiveByCourtId(RESERVATION_2000.getCourt().getId());

        assertEquals(List.of(RESERVATION_2000), results);
    }

    @Test
    void findActiveByPhoneNumber() {
        when(dao.findActiveByPhoneNumber(RESERVATION.getCustomer().getPhoneNumber()))
                .thenReturn(List.of(RESERVATION_ENTITY));
        when(mapper.toModel(RESERVATION_ENTITY)).thenReturn(RESERVATION);

        List<Reservation> results = repository.findActiveByPhoneNumber(RESERVATION.getCustomer().getPhoneNumber());

        assertEquals(List.of(RESERVATION), results);
    }

    @Test
    void findActiveFutureByPhoneNumber() {
        when(dao.findActiveFutureByPhoneNumber(RESERVATION_2000.getCustomer().getPhoneNumber()))
                .thenReturn(List.of(RESERVATION_2000_ENTITY));
        when(mapper.toModel(RESERVATION_2000_ENTITY)).thenReturn(RESERVATION_2000);

        List<Reservation> results = repository.findActiveFutureByPhoneNumber(RESERVATION_2000.getCustomer().getPhoneNumber());

        assertEquals(List.of(RESERVATION_2000), results);
    }
}
