package cz.tondracek.inqooltennis.reservation.service;

import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.court.data.CourtRepository;
import cz.tondracek.inqooltennis.court.mapper.CourtMapper;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.reservation.data.ReservationRepository;
import cz.tondracek.inqooltennis.reservation.dto.CourtReservationListDto;
import cz.tondracek.inqooltennis.reservation.dto.CreateReservationDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationDetailDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationPreviewDto;
import cz.tondracek.inqooltennis.reservation.dto.UpdateReservationDto;
import cz.tondracek.inqooltennis.reservation.mapper.ReservationMapper;
import cz.tondracek.inqooltennis.reservation.model.Reservation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final CourtRepository courtRepository;

    private final CourtMapper courtMapper;
    private final ReservationMapper mapper;

    public ReservationServiceImpl(
            ReservationRepository repository,
            CourtRepository courtRepository,
            CourtMapper courtMapper,
            ReservationMapper reservationMapper
    ) {
        this.repository = repository;
        this.courtRepository = courtRepository;

        this.courtMapper = courtMapper;
        this.mapper = reservationMapper;
    }

    @Transactional
    @Override
    public ReservationDetailDto createReservation(CreateReservationDto createDto) {
        Court court = courtRepository.findActiveById(createDto.courtId());
        Price price = calculatePrice(court, createDto.startTime(), createDto.endTime());
        Reservation newReservation = mapper.toReservation(createDto, price, court);

        if (isOverlappingExisting(newReservation))
            throw new ConflictException("Reservation overlaps with an existing one.");
        repository.create(newReservation);
        return mapper.toDetailDto(newReservation);
    }

    @Transactional
    @Override
    public ReservationDetailDto updateReservation(UUID id, UpdateReservationDto updateDto) {
        Reservation original = repository.findById(id);

        Court newCourt = courtRepository.findActiveById(updateDto.courtId());
        Price newPrice = calculatePrice(newCourt, updateDto.startTime(), updateDto.endTime());
        Reservation updatedReservation = mapper.toReservation(updateDto, original, newPrice, newCourt);

        if (isOverlappingExisting(updatedReservation))
            throw new ConflictException("Reservation overlaps with an existing one.");

        repository.update(updatedReservation);
        return mapper.toDetailDto(updatedReservation);
    }

    @Transactional
    @Override
    public void softDeleteReservation(UUID id) {
        Reservation reservation = repository.findById(id);

        Reservation deleted = reservation.withDeleted(true);
        repository.update(deleted);
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationDetailDto getReservationById(UUID id) {
        Reservation reservation = repository.findById(id);
        return mapper.toDetailDto(reservation);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationPreviewDto> getActiveReservations() {
        return repository.findAllActive()
                .stream()
                .map(mapper::toPreviewDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CourtReservationListDto getCourtReservations(UUID courtId) {
        Court court = courtRepository.findActiveById(courtId);
        List<Reservation> reservations = repository.findActiveByCourtId(courtId);

        return new CourtReservationListDto(
                courtMapper.toDetailDto(court),
                reservations.stream().map(mapper::toPreviewDto).toList()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationPreviewDto> getReservationsByPhoneNumber(String phoneNumber, boolean onlyFuture) {
        List<Reservation> reservations = onlyFuture
                ? repository.findActiveFutureByPhoneNumber(phoneNumber)
                : repository.findActiveByPhoneNumber(phoneNumber);

        return reservations.stream()
                .map(mapper::toPreviewDto)
                .toList();
    }

    private boolean isOverlappingExisting(Reservation newReservation) {
        List<Reservation> activeByCourtId = repository.findActiveByCourtId(newReservation.getCourt().getId());
        return activeByCourtId.stream().anyMatch((existing) -> {
                    boolean overlaps = newReservation.getStartTime().isBefore(existing.getEndTime()) &&
                                       newReservation.getEndTime().isAfter(existing.getStartTime());
                    return !existing.getId().equals((newReservation.getId())) && overlaps;
                }
        );
    }

    private Price calculatePrice(Court court, LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        Price pricePerMinute = court.getSurfaceType().getPricePerMinute();

        return new Price(
                pricePerMinute.getAmount().multiply(BigDecimal.valueOf(duration.toMinutes())),
                pricePerMinute.getCurrencyCode()
        );
    }
}
