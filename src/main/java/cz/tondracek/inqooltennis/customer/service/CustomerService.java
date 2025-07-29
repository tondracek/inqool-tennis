package cz.tondracek.inqooltennis.customer.service;

import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.customer.dto.CreateCustomerDto;
import cz.tondracek.inqooltennis.customer.dto.CustomerDetailDto;
import cz.tondracek.inqooltennis.customer.dto.UpdateCustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    /**
     * @param createDto values for creating a new customer
     * @return the created customer details
     * @throws ConflictException if a customer with the same phone number already exists
     */
    CustomerDetailDto createCustomer(CreateCustomerDto createDto);

    /**
     * @param id        the UUID of the customer to update
     * @param updateDto values to be updated for the customer
     * @return the updated customer details
     * @throws ConflictException if a customer with the same phone number already exists
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    CustomerDetailDto updateCustomer(UUID id, UpdateCustomerDto updateDto);

    /**
     * @param id the UUID of the customer to be soft deleted (set deleted = true)
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    void softDeleteCustomer(UUID id);

    /**
     * @param id the UUID of the customer to retrieve
     * @return the details of the customer with the given ID
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    CustomerDetailDto getCustomerById(UUID id);

    /**
     * @return a list of all customers
     */
    List<CustomerDetailDto> getAllCustomers();
}
