package cz.tondracek.inqooltennis.customer.mapper;

import cz.tondracek.inqooltennis.customer.data.CustomerEntity;
import cz.tondracek.inqooltennis.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerEntityMapperTest {

    @Autowired
    CustomerEntityMapper customerEntityMapper;

    @Test
    void toEntity() {
        CustomerEntity entity = customerEntityMapper.toEntity(CUSTOMER);

        assertNotNull(entity);
        assertEquals(CUSTOMER_ENTITY.getId(), entity.getId());
        assertEquals(CUSTOMER_ENTITY.getName(), entity.getName());
        assertEquals(CUSTOMER_ENTITY.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(CUSTOMER_ENTITY.isDeleted(), entity.isDeleted());
    }

    @Test
    void toModel() {
        Customer model = customerEntityMapper.toModel(CUSTOMER_ENTITY);

        assertNotNull(model);
        assertEquals(CUSTOMER.getId(), model.getId());
        assertEquals(CUSTOMER.getName(), model.getName());
        assertEquals(CUSTOMER.getPhoneNumber(), model.getPhoneNumber());
        assertEquals(CUSTOMER.isDeleted(), model.isDeleted());
    }
}
