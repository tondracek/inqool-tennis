package cz.tondracek.inqooltennis.customer.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(CustomerEntity entity) {
        entityManager.persist(entity);
    }

    public void update(CustomerEntity entity) {
        entityManager.merge(entity);
    }

    public Optional<CustomerEntity> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(CustomerEntity.class, id));
    }

    public List<CustomerEntity> findAllActive() {
        String query = "SELECT c FROM CustomerEntity c WHERE c.deleted = false ORDER BY c.name ASC";
        return entityManager.createQuery(query, CustomerEntity.class).getResultList();
    }

    public Optional<CustomerEntity> findByPhoneNumber(String phoneNumber) {
        String query = "SELECT c FROM CustomerEntity c WHERE c.phoneNumber = :phoneNumber AND c.deleted = false";
        try {
            return Optional.ofNullable(entityManager.createQuery(query, CustomerEntity.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
