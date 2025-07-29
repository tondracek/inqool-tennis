package cz.tondracek.inqooltennis.customer.mapper;

import cz.tondracek.inqooltennis.customer.dto.CustomerDetailDto;
import cz.tondracek.inqooltennis.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static cz.tondracek.inqooltennis.customer.CustomerSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_DTO;
import static cz.tondracek.inqooltennis.customer.CustomerSample.UPDATED_CUSTOMER;
import static cz.tondracek.inqooltennis.customer.CustomerSample.UPDATE_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerMapperTest {

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void createToCustomer() {
        Customer result = customerMapper.toCustomer(CREATE_DTO, CUSTOMER.getId());

        assertNotNull(result);
        assertEquals(CUSTOMER, result);
    }

    @Test
    void updateToCustomer() {
        Customer updated = customerMapper.toCustomer(UPDATE_DTO, CUSTOMER);

        assertNotNull(updated);
        assertEquals(UPDATED_CUSTOMER, updated);
    }

    @Test
    void toDetailDto() {
        CustomerDetailDto dto = customerMapper.toDetailDto(CUSTOMER);

        assertNotNull(dto);
        assertEquals(CUSTOMER_DTO, dto);
    }
}
