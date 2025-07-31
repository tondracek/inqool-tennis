package cz.tondracek.inqooltennis.customer.service;

import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.customer.data.CustomerRepository;
import cz.tondracek.inqooltennis.customer.dto.CreateCustomerDto;
import cz.tondracek.inqooltennis.customer.dto.CustomerDetailDto;
import cz.tondracek.inqooltennis.customer.dto.UpdateCustomerDto;
import cz.tondracek.inqooltennis.customer.mapper.CustomerMapper;
import cz.tondracek.inqooltennis.customer.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            CustomerMapper customerMapper
    ) {
        this.repository = customerRepository;
        this.mapper = customerMapper;
    }

    @Transactional
    @Override
    public CustomerDetailDto createCustomer(CreateCustomerDto createDto) {
        Customer newCustomer = mapper.toCustomer(createDto, UUID.randomUUID());

        checkForCustomerWithExistingPhoneNumber(newCustomer);

        repository.create(newCustomer);
        return mapper.toDetailDto(newCustomer);
    }

    @Transactional
    @Override
    public CustomerDetailDto updateCustomer(UUID id, UpdateCustomerDto updateDto) {
        Customer original = repository.findById(id);
        Customer updated = mapper.toCustomer(updateDto, original);

        checkForCustomerWithExistingPhoneNumber(updated);

        repository.update(updated);
        return mapper.toDetailDto(updated);
    }

    @Transactional
    @Override
    public void softDeleteCustomer(UUID id) {
        Customer original = repository.findById(id);
        Customer deleted = original.withDeleted(true);

        repository.update(deleted);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerDetailDto getCustomerById(UUID id) {
        Customer customer = repository.findById(id);
        return mapper.toDetailDto(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerDetailDto> getAllCustomers() {
        return repository.findAllActive().stream()
                .map(mapper::toDetailDto)
                .toList();
    }

    private void checkForCustomerWithExistingPhoneNumber(Customer newCustomer) {
        Customer userWithPhoneNumber;
        try {
            userWithPhoneNumber = repository.findByPhoneNumber(newCustomer.getPhoneNumber());
        } catch (NotFoundException e) {
            userWithPhoneNumber = null;
        }
        if (userWithPhoneNumber != null && !userWithPhoneNumber.getId().equals(newCustomer.getId()))
            throw new ConflictException("Customer with this phone number already exists.");
    }
}
