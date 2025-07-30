package cz.tondracek.inqooltennis.customer.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerDao {

    void save(CustomerEntity entity);

    void update(CustomerEntity entity);

    Optional<CustomerEntity> findById(UUID id);

    List<CustomerEntity> findAllActive();

    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);

    void flush();
}