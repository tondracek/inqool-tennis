package cz.tondracek.inqooltennis.customer.mapper;

import cz.tondracek.inqooltennis.customer.dto.CreateCustomerDto;
import cz.tondracek.inqooltennis.customer.dto.CustomerDetailDto;
import cz.tondracek.inqooltennis.customer.dto.UpdateCustomerDto;
import cz.tondracek.inqooltennis.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "createDto.name")
    @Mapping(target = "phoneNumber", source = "createDto.phoneNumber")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "withDeleted", ignore = true)
    Customer toCustomer(CreateCustomerDto createDto, UUID id);

    @Mapping(target = "id", source = "original.id")
    @Mapping(target = "deleted", source = "original.deleted")
    @Mapping(target = "name", source = "update.name")
    @Mapping(target = "phoneNumber", source = "update.phoneNumber")
    @Mapping(target = "withDeleted", ignore = true)
    Customer toCustomer(UpdateCustomerDto update, Customer original);

    @Mapping(target = "id", source = "customer.id")
    @Mapping(target = "name", source = "customer.name")
    @Mapping(target = "phoneNumber", source = "customer.phoneNumber")
    @Mapping(target = "deleted", source = "customer.deleted")
    CustomerDetailDto toDetailDto(Customer customer);
}
