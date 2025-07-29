package cz.tondracek.inqooltennis.customer.data;


import cz.tondracek.inqooltennis.common.baseentity.data.BaseDeletableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerEntity extends BaseDeletableEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    public CustomerEntity(UUID id, String name, String phoneNumber, boolean deleted) {
        super(id, deleted);
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
