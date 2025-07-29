package cz.tondracek.inqooltennis.customer.model;

import cz.tondracek.inqooltennis.common.SoftDeletable;
import lombok.Value;

import java.util.UUID;

@Value
public class Customer implements SoftDeletable<Customer> {
    UUID id;
    String name;
    String phoneNumber;

    boolean deleted;

    @Override
    public Customer withDeleted(boolean deletedValue) {
        return new Customer(
                this.id,
                this.name,
                this.phoneNumber,
                deletedValue
        );
    }
}
