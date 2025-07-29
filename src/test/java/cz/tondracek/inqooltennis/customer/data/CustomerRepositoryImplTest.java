package cz.tondracek.inqooltennis.customer.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.customer.mapper.CustomerEntityMapper;
import cz.tondracek.inqooltennis.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryImplTest {

    @Mock
    private CustomerDao dao;

    @Mock
    private CustomerEntityMapper mapper;

    @InjectMocks
    private CustomerRepositoryImpl repository;

    @Test
    void create() {
        when(mapper.toEntity(CUSTOMER)).thenReturn(CUSTOMER_ENTITY);

        Customer result = repository.create(CUSTOMER);

        assertEquals(CUSTOMER, result);
    }

    @Test
    void update() {
        when(mapper.toEntity(CUSTOMER)).thenReturn(CUSTOMER_ENTITY);

        Customer result = repository.update(CUSTOMER);

        assertEquals(CUSTOMER, result);
    }

    @Test
    void findById() {
        when(dao.findById(CUSTOMER_ENTITY.getId())).thenReturn(Optional.of(CUSTOMER_ENTITY));
        when(mapper.toModel(CUSTOMER_ENTITY)).thenReturn(CUSTOMER);

        Customer result = repository.findById(CUSTOMER_ENTITY.getId());

        assertEquals(CUSTOMER, result);
    }

    @Test
    void findById_shouldFailOnNotFound() {
        when(dao.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> repository.findById(UUID.randomUUID()));
    }

    @Test
    void findByPhoneNumber() {
        when(dao.findByPhoneNumber(CUSTOMER_ENTITY.getPhoneNumber())).thenReturn(Optional.of(CUSTOMER_ENTITY));
        when(mapper.toModel(CUSTOMER_ENTITY)).thenReturn(CUSTOMER);

        Customer result = repository.findByPhoneNumber(CUSTOMER_ENTITY.getPhoneNumber());

        assertEquals(CUSTOMER, result);
    }

    @Test
    void findByPhoneNumber_shouldFailOnNotFound() {
        when(dao.findByPhoneNumber(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> repository.findByPhoneNumber("999999999"));
    }

    @Test
    void findAllActive() {
        List<CustomerEntity> entities = List.of(CUSTOMER_ENTITY, CUSTOMER_ENTITY, CUSTOMER_ENTITY);

        when(dao.findAllActive()).thenReturn(entities);
        when(mapper.toModel(CUSTOMER_ENTITY)).thenReturn(CUSTOMER);

        List<Customer> result = repository.findAllActive();

        assertEquals(3, result.size());
        for (Customer customer : result) {
            assertEquals(CUSTOMER, customer);
        }
    }
}
