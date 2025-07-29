package cz.tondracek.inqooltennis.customer.service;

import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.customer.data.CustomerRepository;
import cz.tondracek.inqooltennis.customer.dto.CustomerDetailDto;
import cz.tondracek.inqooltennis.customer.mapper.CustomerMapper;
import cz.tondracek.inqooltennis.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static cz.tondracek.inqooltennis.customer.CustomerSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_2;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_2_DTO;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_DELETED;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_DTO;
import static cz.tondracek.inqooltennis.customer.CustomerSample.UPDATED_CUSTOMER;
import static cz.tondracek.inqooltennis.customer.CustomerSample.UPDATED_CUSTOMER_DTO;
import static cz.tondracek.inqooltennis.customer.CustomerSample.UPDATE_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerServiceImpl service;

    @Test
    void createCustomer() {
        when(mapper.toCustomer(eq(CREATE_DTO), any(UUID.class))).thenReturn(CUSTOMER);
        when(repository.findByPhoneNumber(CUSTOMER.getPhoneNumber())).thenThrow(NotFoundException.class);
        when(mapper.toDetailDto(CUSTOMER)).thenReturn(CUSTOMER_DTO);

        CustomerDetailDto result = service.createCustomer(CREATE_DTO);

        assertEquals(CUSTOMER_DTO, result);
    }

    @Test
    void createCustomer_shouldFailOnDuplicatePhoneNumber() {
        when(mapper.toCustomer(eq(CREATE_DTO), any(UUID.class))).thenReturn(CUSTOMER);
        when(repository.findByPhoneNumber(CUSTOMER.getPhoneNumber())).thenReturn(CUSTOMER);

        assertThrows(ConflictException.class, () -> service.createCustomer(CREATE_DTO));
    }

    @Test
    void updateCustomer() {
        when(repository.findById(CUSTOMER.getId())).thenReturn(CUSTOMER);
        when(mapper.toCustomer(UPDATE_DTO, CUSTOMER)).thenReturn(UPDATED_CUSTOMER);
        when(repository.findByPhoneNumber(UPDATED_CUSTOMER.getPhoneNumber())).thenThrow(NotFoundException.class);
        when(mapper.toDetailDto(UPDATED_CUSTOMER)).thenReturn(UPDATED_CUSTOMER_DTO);

        CustomerDetailDto result = service.updateCustomer(CUSTOMER.getId(), UPDATE_DTO);

        assertEquals(UPDATED_CUSTOMER_DTO, result);
    }

    @Test
    void updateCustomer_2() {
        when(repository.findById(CUSTOMER.getId())).thenReturn(CUSTOMER);
        when(mapper.toCustomer(UPDATE_DTO, CUSTOMER)).thenReturn(UPDATED_CUSTOMER);
        when(repository.findByPhoneNumber(UPDATED_CUSTOMER.getPhoneNumber())).thenReturn(CUSTOMER);
        when(mapper.toDetailDto(UPDATED_CUSTOMER)).thenReturn(UPDATED_CUSTOMER_DTO);

        CustomerDetailDto result = service.updateCustomer(CUSTOMER.getId(), UPDATE_DTO);

        assertEquals(UPDATED_CUSTOMER_DTO, result);
    }


    @Test
    void updateCustomer_shouldFailOnPhoneConflict() {
        Customer duplicatePhoneCustomer = new Customer(
                UUID.randomUUID(),
                "Duplicate Customer",
                UPDATED_CUSTOMER.getPhoneNumber(),
                false
        );

        when(repository.findById(CUSTOMER.getId())).thenReturn(CUSTOMER);
        when(mapper.toCustomer(UPDATE_DTO, CUSTOMER)).thenReturn(UPDATED_CUSTOMER);
        when(repository.findByPhoneNumber(UPDATED_CUSTOMER.getPhoneNumber())).thenReturn(duplicatePhoneCustomer);

        assertThrows(ConflictException.class, () -> service.updateCustomer(CUSTOMER.getId(), UPDATE_DTO));
    }

    @Test
    void softDeleteCustomer() {
        when(repository.findById(CUSTOMER.getId())).thenReturn(CUSTOMER);
        when(repository.update(CUSTOMER_DELETED)).thenReturn(CUSTOMER_DELETED);

        service.softDeleteCustomer(CUSTOMER.getId());

        Mockito.verify(repository).update(CUSTOMER_DELETED);
    }

    @Test
    void getCustomerById() {
        when(repository.findById(CUSTOMER.getId())).thenReturn(CUSTOMER);
        when(mapper.toDetailDto(CUSTOMER)).thenReturn(CUSTOMER_DTO);

        CustomerDetailDto result = service.getCustomerById(CUSTOMER.getId());

        assertEquals(CUSTOMER_DTO, result);
    }

    @Test
    void getAllCustomers_shouldReturnMappedList() {
        when(repository.findAllActive()).thenReturn(List.of(CUSTOMER, CUSTOMER_2));
        when(mapper.toDetailDto(CUSTOMER)).thenReturn(CUSTOMER_DTO);
        when(mapper.toDetailDto(CUSTOMER_2)).thenReturn(CUSTOMER_2_DTO);

        List<CustomerDetailDto> result = service.getAllCustomers();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(CUSTOMER_DTO, CUSTOMER_2_DTO)));
    }
}
