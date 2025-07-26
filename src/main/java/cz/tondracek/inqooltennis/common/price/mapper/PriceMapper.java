package cz.tondracek.inqooltennis.common.price.mapper;

import cz.tondracek.inqooltennis.common.price.dto.PriceDto;
import cz.tondracek.inqooltennis.common.price.model.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    Price toModel(PriceDto dto);

    PriceDto toDto(Price price);
}
