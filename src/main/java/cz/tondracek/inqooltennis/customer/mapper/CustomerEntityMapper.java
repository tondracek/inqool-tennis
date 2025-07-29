package cz.tondracek.inqooltennis.customer.mapper;

import cz.tondracek.inqooltennis.customer.data.CustomerEntity;
import cz.tondracek.inqooltennis.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {

    @Mapping(target = "name", source = "model.name")
    @Mapping(target = "phoneNumber", source = "model.phoneNumber")
    @Mapping(target = "createdAt", ignore = true)
    CustomerEntity toEntity(Customer model);

    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "phoneNumber", source = "entity.phoneNumber")
    @Mapping(target = "withDeleted", ignore = true)
    Customer toModel(CustomerEntity entity);
}
