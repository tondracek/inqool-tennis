package cz.tondracek.inqooltennis.customer.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.customer.mapper.CustomerEntityMapper;
import cz.tondracek.inqooltennis.customer.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerDao dao;
    private final CustomerEntityMapper mapper;

    public CustomerRepositoryImpl(
            CustomerDao customerDao,
            CustomerEntityMapper customerEntityMapper
    ) {
        this.dao = customerDao;
        this.mapper = customerEntityMapper;
    }

    @Override
    public Customer create(Customer customer) {
        CustomerEntity customerEntity = mapper.toEntity(customer);
        dao.save(customerEntity);

        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        CustomerEntity customerEntity = mapper.toEntity(customer);
        dao.update(customerEntity);

        return customer;
    }

    @Override
    public Customer findById(UUID id) {
        return dao.findById(id)
                .map(mapper::toModel)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Customer> findAllActive() {
        return dao.findAllActive()
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Customer findByPhoneNumber(String phoneNumber) {
        return dao.findByPhoneNumber(phoneNumber)
                .map(mapper::toModel)
                .orElseThrow(NotFoundException::new);
    }
}
