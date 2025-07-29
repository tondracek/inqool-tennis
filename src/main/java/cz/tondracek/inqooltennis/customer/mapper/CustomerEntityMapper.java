package cz.tondracek.inqooltennis.customer.mapper;

import cz.tondracek.inqooltennis.customer.data.CustomerEntity;
import cz.tondracek.inqooltennis.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {

    @Mapping(target = "createdAt", ignore = true)
    CustomerEntity toEntity(Customer model);

    @Mapping(target = "withDeleted", ignore = true)
    Customer toModel(CustomerEntity entity);
}
