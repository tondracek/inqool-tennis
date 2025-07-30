package cz.tondracek.inqooltennis.reservation.mapper;

import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.court.mapper.CourtMapper;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.customer.mapper.CustomerMapper;
import cz.tondracek.inqooltennis.customer.model.Customer;
import cz.tondracek.inqooltennis.reservation.dto.CreateReservationDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationDetailDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationPreviewDto;
import cz.tondracek.inqooltennis.reservation.dto.UpdateReservationDto;
import cz.tondracek.inqooltennis.reservation.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CourtMapper.class, CustomerMapper.class})
public interface ReservationMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "withDeleted", ignore = true)
    Reservation toReservation(CreateReservationDto create, Customer customer, Price price, Court court);

    @Mapping(target = "id", source = "original.id")
    @Mapping(target = "court", source = "court")
    @Mapping(target = "customer", source = "original.customer")
    @Mapping(target = "startTime", source = "update.startTime")
    @Mapping(target = "endTime", source = "update.endTime")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "gameType", source = "update.gameType")
    @Mapping(target = "deleted", source = "original.deleted")
    @Mapping(target = "createdAt", source = "original.createdAt")
    @Mapping(target = "withDeleted", ignore = true)
    Reservation toReservation(UpdateReservationDto update, Reservation original, Price price, Court court);

    @Mapping(target = "courtDetailDto", source = "reservation.court")
    @Mapping(target = "customerDetailDto", source = "reservation.customer")
    ReservationDetailDto toDetailDto(Reservation reservation);

    @Mapping(target = "courtName", source = "reservation.court.name")
    @Mapping(target = "customerName", source = "reservation.customer.name")
    ReservationPreviewDto toPreviewDto(Reservation reservation);
}
