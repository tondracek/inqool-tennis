package cz.tondracek.inqooltennis.customer.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.customer.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository {

    /**
     * @param customer the customer to be created
     * @return the created customer
     */
    Customer create(Customer customer);

    /**
     * @param customer the customer to be updated
     * @return the updated customer
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    Customer update(Customer customer);

    /**
     * ⚠️ Can return court with deleted=true
     *
     * @param id the UUID of the customer to find
     * @return the customer with the given ID
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    Customer findById(UUID id);

    /**
     * @return a list of all non-deleted customers
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    List<Customer> findAllActive();

    /**
     * @param phoneNumber the phone number of the customer to find
     * @return Customer with the given phone number, if exists
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    Customer findByPhoneNumber(String phoneNumber);
}
