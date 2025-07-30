package cz.tondracek.inqooltennis.customer.mapper;

import cz.tondracek.inqooltennis.customer.data.CustomerEntity;
import cz.tondracek.inqooltennis.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_2;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_2_ENTITY;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerEntityMapperTest {

    @Autowired
    CustomerEntityMapper customerEntityMapper;

    @Test
    void toEntity() {
        CustomerEntity entity = customerEntityMapper.toEntity(CUSTOMER_2);

        assertNotNull(entity);
        assertEquals(CUSTOMER_2_ENTITY.getId(), entity.getId());
        assertEquals(CUSTOMER_2_ENTITY.getName(), entity.getName());
        assertEquals(CUSTOMER_2_ENTITY.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(CUSTOMER_2_ENTITY.isDeleted(), entity.isDeleted());
    }

    @Test
    void toModel() {
        Customer model = customerEntityMapper.toModel(CUSTOMER_2_ENTITY);

        assertNotNull(model);
        assertEquals(CUSTOMER_2.getId(), model.getId());
        assertEquals(CUSTOMER_2.getName(), model.getName());
        assertEquals(CUSTOMER_2.getPhoneNumber(), model.getPhoneNumber());
        assertEquals(CUSTOMER_2.isDeleted(), model.isDeleted());
    }
}
