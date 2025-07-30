package cz.tondracek.inqooltennis.customer.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_2_ENTITY;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_ENTITY;
import static cz.tondracek.inqooltennis.customer.CustomerSample.UPDATED_CUSTOMER_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(CustomerDaoImpl.class)
class CustomerDaoImplTest {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void save() {
        customerDao.save(CUSTOMER_ENTITY);

        CustomerEntity result = entityManager.find(CustomerEntity.class, CUSTOMER_ENTITY.getId());

        assertNotNull(result);
        assertEquals(CUSTOMER_ENTITY, result);
    }

    @Test
    void update() {
        customerDao.save(CUSTOMER_ENTITY);

        customerDao.update(UPDATED_CUSTOMER_ENTITY);

        CustomerEntity result = entityManager.find(CustomerEntity.class, CUSTOMER_ENTITY.getId());

        assertNotNull(result);
        assertEquals(UPDATED_CUSTOMER_ENTITY.getId(), result.getId());
        assertEquals(UPDATED_CUSTOMER_ENTITY, result);
    }

    @Test
    void findById() {
        entityManager.persist(CUSTOMER_ENTITY);

        CustomerEntity result = customerDao.findById(CUSTOMER_ENTITY.getId())
                .orElse(null);

        assertEquals(CUSTOMER_ENTITY, result);
    }

    @Test
    void findByPhoneNumber() {
        entityManager.persist(CUSTOMER_ENTITY);

        CustomerEntity result = customerDao.findByPhoneNumber(CUSTOMER_ENTITY.getPhoneNumber())
                .orElse(null);

        assertEquals(CUSTOMER_ENTITY, result);
    }

    @Test
    void findByPhoneNumber_shouldThrowOnNotFound() {
        entityManager.persist(CUSTOMER_ENTITY);

        Optional<CustomerEntity> result = customerDao.findByPhoneNumber(CUSTOMER_2_ENTITY.getPhoneNumber());

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllActive() {
        Map<UUID, CustomerEntity> customers = Stream.of(
                generateCustomer(false, "123"),
                generateCustomer(true, "124"),
                generateCustomer(false, "125"),
                generateCustomer(true, "126")
        ).collect(Collectors.toMap(CustomerEntity::getId, c -> c));

        customers.values().forEach(entityManager::persist);

        var result = customerDao.findAllActive();

        assertNotNull(result);
        assertEquals(2, result.size());

        for (CustomerEntity customer : result) {
            CustomerEntity original = customers.get(customer.getId());
            assertEquals(original, customer);
            assertFalse(customer.isDeleted());
        }
    }

    private CustomerEntity generateCustomer(boolean deleted, String phoneNumber) {
        return new CustomerEntity(
                UUID.randomUUID(),
                "Generated Customer",
                phoneNumber,
                deleted
        );
    }

    @Test
    void flush() {
        CustomerEntity entity = new CustomerEntity(
                UUID.randomUUID(),
                "Flush Test Customer",
                "123456789",
                false
        );

        customerDao.save(entity);
        customerDao.flush();

        CustomerEntity result = entityManager.find(CustomerEntity.class, entity.getId());

        assertNotNull(result);
        assertEquals(entity, result);
    }
}
