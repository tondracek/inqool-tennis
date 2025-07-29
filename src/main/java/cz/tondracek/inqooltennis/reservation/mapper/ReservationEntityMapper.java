package cz.tondracek.inqooltennis.reservation.mapper;

import cz.tondracek.inqooltennis.court.mapper.CourtEntityMapper;
import cz.tondracek.inqooltennis.customer.mapper.CustomerEntityMapper;
import cz.tondracek.inqooltennis.reservation.data.ReservationEntity;
import cz.tondracek.inqooltennis.reservation.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CustomerEntityMapper.class, CourtEntityMapper.class})
public interface ReservationEntityMapper {

    @Mapping(target = "withDeleted", ignore = true)
    Reservation toModel(ReservationEntity entity);

    ReservationEntity toEntity(Reservation model);
}
