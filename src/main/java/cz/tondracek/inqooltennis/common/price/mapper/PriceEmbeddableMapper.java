package cz.tondracek.inqooltennis.common.price.mapper;

import cz.tondracek.inqooltennis.common.price.data.PriceEmbeddable;
import cz.tondracek.inqooltennis.common.price.model.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceEmbeddableMapper {

    PriceEmbeddable toEmbeddable(Price model);

    Price toModel(PriceEmbeddable entity);
}
