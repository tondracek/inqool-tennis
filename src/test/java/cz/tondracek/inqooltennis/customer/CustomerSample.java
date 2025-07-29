package cz.tondracek.inqooltennis.customer;

import cz.tondracek.inqooltennis.customer.data.CustomerEntity;
import cz.tondracek.inqooltennis.customer.dto.CreateCustomerDto;
import cz.tondracek.inqooltennis.customer.dto.CustomerDetailDto;
import cz.tondracek.inqooltennis.customer.dto.UpdateCustomerDto;
import cz.tondracek.inqooltennis.customer.model.Customer;

import java.util.UUID;

public class CustomerSample {

    private static final UUID SAMPLE_ID = UUID.randomUUID();

    public static final CreateCustomerDto CREATE_DTO = new CreateCustomerDto(
            "Customer 1",
            "+420123456789"
    );

    public static final Customer CUSTOMER = new Customer(
            SAMPLE_ID,
            "Customer 1",
            "+420123456789",
            false
    );
    public static final Customer CUSTOMER_DELETED = CUSTOMER.withDeleted(true);
    public static final CustomerEntity CUSTOMER_ENTITY = new CustomerEntity(
            SAMPLE_ID,
            "Customer 1",
            "+420123456789",
            false
    );
    public static final CustomerDetailDto CUSTOMER_DTO = new CustomerDetailDto(
            SAMPLE_ID,
            "Customer 1",
            "+420123456789",
            false
    );

    public static final UpdateCustomerDto UPDATE_DTO = new UpdateCustomerDto(
            "Customer Updated",
            "+420 000 000 000"
    );
    public static final Customer UPDATED_CUSTOMER = new Customer(
            SAMPLE_ID,
            "Customer Updated",
            "+420 000 000 000",
            false
    );
    public static final CustomerEntity UPDATED_CUSTOMER_ENTITY = new CustomerEntity(
            SAMPLE_ID,
            "Customer Updated",
            "+420 000 000 000",
            false
    );
    public static final CustomerDetailDto UPDATED_CUSTOMER_DTO = new CustomerDetailDto(
            SAMPLE_ID,
            "Customer Updated",
            "+420 000 000 000",
            false
    );

    private static final UUID CUSTOMER_2_ID = UUID.randomUUID();
    public static final Customer CUSTOMER_2 = new Customer(
            CUSTOMER_2_ID,
            "Customer 2",
            "+420 444 444 444",
            false
    );
    public static final CustomerEntity CUSTOMER_2_ENTITY = new CustomerEntity(
            CUSTOMER_2_ID,
            "Customer 2",
            "+420 444 444 444",
            false
    );
    public static final CustomerDetailDto CUSTOMER_2_DTO = new CustomerDetailDto(
            CUSTOMER_2_ID,
            "Customer 2",
            "+420 444 444 444",
            false
    );
}
