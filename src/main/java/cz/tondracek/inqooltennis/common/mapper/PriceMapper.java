package cz.tondracek.inqooltennis.common.mapper;

import cz.tondracek.inqooltennis.common.dto.PriceDto;
import cz.tondracek.inqooltennis.common.model.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    Price toModel(PriceDto dto);

    PriceDto toDto(Price price);
}
