package cz.tondracek.inqooltennis.reservation.service;

import cz.tondracek.inqooltennis.common.gametype.model.GameType;
import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.core.exception.BadRequestException;
import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.court.data.CourtRepository;
import cz.tondracek.inqooltennis.court.mapper.CourtMapper;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.customer.data.CustomerRepository;
import cz.tondracek.inqooltennis.customer.dto.CreateCustomerDto;
import cz.tondracek.inqooltennis.customer.mapper.CustomerMapper;
import cz.tondracek.inqooltennis.customer.model.Customer;
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
    private final CustomerRepository customerRepository;

    private final ReservationMapper mapper;
    private final CourtMapper courtMapper;
    private final CustomerMapper customerMapper;

    public ReservationServiceImpl(
            ReservationRepository repository,
            CourtRepository courtRepository,
            CustomerRepository customerRepository,
            CourtMapper courtMapper,
            ReservationMapper reservationMapper,
            CustomerMapper customerMapper
    ) {
        this.repository = repository;
        this.courtRepository = courtRepository;
        this.customerRepository = customerRepository;

        this.courtMapper = courtMapper;
        this.mapper = reservationMapper;
        this.customerMapper = customerMapper;
    }

    @Transactional
    @Override
    public ReservationDetailDto createReservation(CreateReservationDto createDto) {
        Customer customer = getCustomerByPhoneNumberOrCreate(createDto);
        Court court = courtRepository.findActiveById(createDto.courtId());
        Price price = calculatePrice(court, createDto.startTime(), createDto.endTime(), createDto.gameType());

        Reservation newReservation = mapper.toReservation(createDto, customer, price, court);

        validateReservationForCreation(newReservation);
        repository.create(newReservation);
        return mapper.toDetailDto(newReservation);
    }

    @Transactional
    @Override
    public ReservationDetailDto updateReservation(UUID id, UpdateReservationDto updateDto) {
        Reservation original = repository.findById(id);

        Court newCourt = courtRepository.findActiveById(updateDto.courtId());
        Price newPrice = calculatePrice(newCourt, updateDto.startTime(), updateDto.endTime(), updateDto.gameType());

        Reservation updatedReservation = mapper.toReservation(updateDto, original, newPrice, newCourt);

        validateReservationForUpdate(updatedReservation);
        repository.update(updatedReservation);
        return mapper.toDetailDto(updatedReservation);
    }

    @Transactional
    @Override
    public void softDeleteReservation(UUID id) {
        Reservation original = repository.findById(id);
        Reservation deleted = original.withDeleted(true);
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

    private Customer getCustomerByPhoneNumberOrCreate(CreateReservationDto createDto) {
        try {
            return customerRepository.findByPhoneNumber(createDto.customerPhoneNumber());
        } catch (NotFoundException e) {
            CreateCustomerDto createCustomerDto = new CreateCustomerDto(
                    createDto.customerPhoneNumber(),
                    createDto.customerName()
            );
            Customer newCustomer = customerMapper.toCustomer(createCustomerDto, UUID.randomUUID());

            return customerRepository.createAndFlush(newCustomer);
        }
    }

    private Price calculatePrice(Court court, LocalDateTime startTime, LocalDateTime endTime, GameType gameType) {
        Duration duration = Duration.between(startTime, endTime);
        Price pricePerMinute = court.getSurfaceType().getPricePerMinute();

        BigDecimal gameTypeMultiplier = gameType == GameType.DOUBLES
                ? BigDecimal.valueOf(1.5)
                : BigDecimal.valueOf(1.0);

        BigDecimal amount = pricePerMinute.getAmount()
                .multiply(BigDecimal.valueOf(duration.toMinutes()))
                .multiply(gameTypeMultiplier);
        String currencyCode = pricePerMinute.getCurrencyCode();

        return new Price(amount, currencyCode);
    }

    private void validateReservationForCreation(Reservation reservation) {
        validateOverlappingExisting(reservation);
        validateTimes(reservation);
    }

    private void validateReservationForUpdate(Reservation reservation) {
        validateOverlappingExisting(reservation);
        validateTimes(reservation);
    }

    private void validateOverlappingExisting(Reservation newReservation) {
        List<Reservation> activeByCourtId = repository.findActiveByCourtId(newReservation.getCourt().getId());

        boolean overlappingExists = activeByCourtId.stream().anyMatch((existing) -> {
                    boolean overlaps = newReservation.getStartTime().isBefore(existing.getEndTime()) &&
                                       newReservation.getEndTime().isAfter(existing.getStartTime());
                    return !existing.getId().equals((newReservation.getId())) && overlaps;
                }
        );

        if (overlappingExists) throw new ConflictException("Reservation overlaps with an existing one.");
    }

    private void validateTimes(Reservation reservation) {
        if (reservation.getStartTime().isAfter(reservation.getEndTime()))
            throw new BadRequestException("Start time must be before end time.");

        if (reservation.getStartTime().isBefore(LocalDateTime.now()))
            throw new BadRequestException("Reservation start time cannot be in the past.");

        if (Duration.between(reservation.getStartTime(), reservation.getEndTime()).toMinutes() < 15)
            throw new BadRequestException("Reservation must be at least 30 minutes long.");
    }
}
