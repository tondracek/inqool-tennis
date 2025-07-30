package cz.tondracek.inqooltennis.reservation.service;

import cz.tondracek.inqooltennis.common.gametype.model.GameType;
import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.core.exception.BadRequestException;
import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.court.CourtSample;
import cz.tondracek.inqooltennis.court.data.CourtRepository;
import cz.tondracek.inqooltennis.court.mapper.CourtMapper;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.customer.data.CustomerRepository;
import cz.tondracek.inqooltennis.customer.dto.CreateCustomerDto;
import cz.tondracek.inqooltennis.customer.mapper.CustomerMapper;
import cz.tondracek.inqooltennis.reservation.data.ReservationRepository;
import cz.tondracek.inqooltennis.reservation.dto.CreateReservationDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationDetailDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationPreviewDto;
import cz.tondracek.inqooltennis.reservation.mapper.ReservationMapper;
import cz.tondracek.inqooltennis.reservation.model.Reservation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static cz.tondracek.inqooltennis.court.CourtSample.COURT;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_CONFLICT;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_DELETED;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_DTO;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_PREVIEW_DTO;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.UPDATED_RESERVATION;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.UPDATED_RESERVATION_DTO;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.UPDATE_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private CourtRepository courtRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ReservationMapper reservationMapper;
    @Mock
    private CourtMapper courtMapper;
    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private ReservationServiceImpl service;

    @Test
    void createReservation() {
        when(customerRepository.findByPhoneNumber(CREATE_DTO.customerPhoneNumber()))
                .thenReturn(CUSTOMER);

        when(courtRepository.findActiveById(CREATE_DTO.courtId()))
                .thenReturn(COURT);

        when(reservationMapper.toReservation(any(CreateReservationDto.class), any(), any(), any()))
                .thenReturn(RESERVATION);

        when(reservationRepository.findActiveByCourtId(COURT.getId()))
                .thenReturn(List.of());

        when(reservationMapper.toDetailDto(RESERVATION))
                .thenReturn(RESERVATION_DTO);

        ReservationDetailDto result = service.createReservation(CREATE_DTO);

        assertEquals(RESERVATION_DTO, result);
        verify(reservationRepository).create(RESERVATION);
    }

    @Test
    void createReservation_shouldCreateNewCustomer() {
        when(customerRepository.findByPhoneNumber(CREATE_DTO.customerPhoneNumber()))
                .thenThrow(NotFoundException.class);
        when(customerMapper.toCustomer(any(CreateCustomerDto.class), any(UUID.class)))
                .thenReturn(CUSTOMER);
        when(customerRepository.createAndFlush(CUSTOMER)).thenReturn(CUSTOMER);

        when(courtRepository.findActiveById(CREATE_DTO.courtId()))
                .thenReturn(COURT);

        when(reservationMapper.toReservation(any(CreateReservationDto.class), any(), any(), any()))
                .thenReturn(RESERVATION);

        when(reservationRepository.findActiveByCourtId(COURT.getId()))
                .thenReturn(List.of());

        when(reservationMapper.toDetailDto(RESERVATION))
                .thenReturn(RESERVATION_DTO);

        ReservationDetailDto result = service.createReservation(CREATE_DTO);

        assertEquals(RESERVATION_DTO, result);
        verify(reservationRepository).create(RESERVATION);
    }

    @Test
    void createReservation_shouldThrowConflictOnOverlapping() {
        when(customerRepository.findByPhoneNumber(CREATE_DTO.customerPhoneNumber()))
                .thenReturn(CUSTOMER);

        when(courtRepository.findActiveById(CREATE_DTO.courtId()))
                .thenReturn(COURT);

        when(reservationMapper.toReservation(any(CreateReservationDto.class), any(), any(), any()))
                .thenReturn(RESERVATION);

        // Simulate an overlapping reservation
        when(reservationRepository.findActiveByCourtId(COURT.getId()))
                .thenReturn(List.of(RESERVATION_CONFLICT));

        assertThrows(ConflictException.class, () -> service.createReservation(CREATE_DTO));
    }

    @Test
    void createReservation_shouldThrowBadRequestOnEndTimeBeforeStart() {
        CreateReservationDto invalidDto = new CreateReservationDto(
                COURT.getId(),
                LocalDateTime.now().plusHours(2),  // end before start
                LocalDateTime.now().plusHours(1),
                GameType.SINGLES,
                CUSTOMER.getName(),
                CUSTOMER.getPhoneNumber()
        );

        when(customerRepository.findByPhoneNumber(invalidDto.customerPhoneNumber()))
                .thenReturn(CUSTOMER);

        when(courtRepository.findActiveById(invalidDto.courtId()))
                .thenReturn(COURT);

        when(reservationMapper.toReservation(any(CreateReservationDto.class), any(), any(), any()))
                .thenAnswer(inv -> new Reservation(
                        UUID.randomUUID(),
                        COURT,
                        CUSTOMER,
                        invalidDto.startTime(),
                        invalidDto.endTime(),
                        new Price(BigDecimal.TEN, "CZK"),
                        GameType.SINGLES,
                        false,
                        LocalDateTime.now()
                ));

        assertThrows(BadRequestException.class, () -> service.createReservation(invalidDto));
    }

    @Test
    void createReservation_shouldThrowBadRequestOnStartTimeInPast() {
        CreateReservationDto invalidDto = new CreateReservationDto(
                COURT.getId(),
                LocalDateTime.now().minusHours(1),  // start in the past
                LocalDateTime.now(),
                GameType.SINGLES,
                CUSTOMER.getName(),
                CUSTOMER.getPhoneNumber()
        );

        when(customerRepository.findByPhoneNumber(invalidDto.customerPhoneNumber()))
                .thenReturn(CUSTOMER);

        when(courtRepository.findActiveById(invalidDto.courtId()))
                .thenReturn(COURT);

        when(reservationMapper.toReservation(any(CreateReservationDto.class), any(), any(), any()))
                .thenAnswer(inv -> new Reservation(
                        UUID.randomUUID(),
                        COURT,
                        CUSTOMER,
                        invalidDto.startTime(),
                        invalidDto.endTime(),
                        new Price(BigDecimal.TEN, "CZK"),
                        GameType.SINGLES,
                        false,
                        LocalDateTime.now()
                ));

        assertThrows(BadRequestException.class, () -> service.createReservation(invalidDto));
    }

    @Test
    void createReservation_shouldThrowBadRequestOnTooShortDuration() {
        CreateReservationDto invalidDto = new CreateReservationDto(
                COURT.getId(),
                LocalDateTime.now().plusMinutes(1),
                LocalDateTime.now().plusMinutes(2),  // less than 30 minutes
                GameType.SINGLES,
                CUSTOMER.getName(),
                CUSTOMER.getPhoneNumber()
        );

        when(customerRepository.findByPhoneNumber(invalidDto.customerPhoneNumber()))
                .thenReturn(CUSTOMER);

        when(courtRepository.findActiveById(invalidDto.courtId()))
                .thenReturn(COURT);

        when(reservationMapper.toReservation(any(CreateReservationDto.class), any(), any(), any()))
                .thenAnswer(inv -> new Reservation(
                        UUID.randomUUID(),
                        COURT,
                        CUSTOMER,
                        invalidDto.startTime(),
                        invalidDto.endTime(),
                        new Price(BigDecimal.TEN, "CZK"),
                        GameType.SINGLES,
                        false,
                        LocalDateTime.now()
                ));

        assertThrows(BadRequestException.class, () -> service.createReservation(invalidDto));
    }

    @Test
    void updateReservation() {
        assertEquals(GameType.DOUBLES, UPDATED_RESERVATION.getGameType());

        when(reservationRepository.findById(RESERVATION.getId())).thenReturn(RESERVATION);
        when(courtRepository.findActiveById(UPDATE_DTO.courtId())).thenReturn(UPDATED_RESERVATION.getCourt());
        when(reservationMapper.toReservation(eq(UPDATE_DTO), eq(RESERVATION), any(Price.class), any(Court.class)))
                .thenReturn(UPDATED_RESERVATION);

        when(reservationRepository.findActiveByCourtId(UPDATED_RESERVATION.getCourt().getId()))
                .thenReturn(List.of(RESERVATION));
        when(reservationRepository.update(UPDATED_RESERVATION)).thenReturn(UPDATED_RESERVATION);
        when(reservationMapper.toDetailDto(UPDATED_RESERVATION)).thenReturn(UPDATED_RESERVATION_DTO);

        ReservationDetailDto result = service.updateReservation(RESERVATION.getId(), UPDATE_DTO);

        assertEquals(UPDATED_RESERVATION_DTO, result);
        verify(reservationRepository).update(UPDATED_RESERVATION);

        assertTrue(result.price().getAmount().compareTo(RESERVATION.getPrice().getAmount()) > 0);
    }

    @Test
    void softDeleteReservation() {
        when(reservationRepository.findById(RESERVATION.getId())).thenReturn(RESERVATION);

        service.softDeleteReservation(RESERVATION.getId());

        verify(reservationRepository).update(RESERVATION_DELETED);
    }

    @Test
    void getReservationById() {
        when(reservationRepository.findById(RESERVATION.getId())).thenReturn(RESERVATION);
        when(reservationMapper.toDetailDto(RESERVATION)).thenReturn(RESERVATION_DTO);

        ReservationDetailDto result = service.getReservationById(RESERVATION.getId());

        assertEquals(RESERVATION_DTO, result);
    }

    @Test
    void getActiveReservations() {
        when(reservationRepository.findAllActive()).thenReturn(List.of(RESERVATION));
        when(reservationMapper.toPreviewDto(RESERVATION)).thenReturn(RESERVATION_PREVIEW_DTO);

        List<ReservationPreviewDto> result = service.getActiveReservations();

        assertEquals(1, result.size());
        assertEquals(RESERVATION_PREVIEW_DTO, result.getFirst());
    }

    @Test
    void getCourtReservations() {
        when(courtRepository.findActiveById(COURT.getId())).thenReturn(COURT);
        when(reservationRepository.findActiveByCourtId(COURT.getId())).thenReturn(List.of(RESERVATION));
        when(courtMapper.toDetailDto(COURT)).thenReturn(CourtSample.COURT_DTO);
        when(reservationMapper.toPreviewDto(RESERVATION)).thenReturn(RESERVATION_PREVIEW_DTO);

        var result = service.getCourtReservations(COURT.getId());

        assertEquals(CourtSample.COURT_DTO, result.court());
        assertEquals(1, result.reservations().size());
        assertEquals(RESERVATION_PREVIEW_DTO, result.reservations().getFirst());
    }

    @Test
    void getReservationsByPhoneNumber_shouldReturnAll() {
        when(reservationRepository.findActiveByPhoneNumber(CUSTOMER.getPhoneNumber()))
                .thenReturn(List.of(RESERVATION));
        when(reservationMapper.toPreviewDto(RESERVATION)).thenReturn(RESERVATION_PREVIEW_DTO);

        List<ReservationPreviewDto> result = service.getReservationsByPhoneNumber(CUSTOMER.getPhoneNumber(), false);

        assertEquals(1, result.size());
        assertEquals(RESERVATION_PREVIEW_DTO, result.getFirst());
    }

    @Test
    void getReservationsByPhoneNumber_shouldReturnOnlyFuture() {
        when(reservationRepository.findActiveFutureByPhoneNumber(CUSTOMER.getPhoneNumber()))
                .thenReturn(List.of(RESERVATION));
        when(reservationMapper.toPreviewDto(RESERVATION)).thenReturn(RESERVATION_PREVIEW_DTO);

        List<ReservationPreviewDto> result = service.getReservationsByPhoneNumber(CUSTOMER.getPhoneNumber(), true);

        assertEquals(1, result.size());
        assertEquals(RESERVATION_PREVIEW_DTO, result.getFirst());
    }

}
