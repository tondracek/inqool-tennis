package cz.tondracek.inqooltennis.reservation.data;

import cz.tondracek.inqooltennis.common.baseentity.data.BaseDeletableEntity;
import cz.tondracek.inqooltennis.common.gametype.model.GameType;
import cz.tondracek.inqooltennis.common.price.data.PriceEmbeddable;
import cz.tondracek.inqooltennis.court.data.CourtEntity;
import cz.tondracek.inqooltennis.customer.data.CustomerEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReservationEntity extends BaseDeletableEntity {

    @ManyToOne(optional = false)
    private CourtEntity court;

    @ManyToOne(optional = false)
    private CustomerEntity customer;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Embedded
    private PriceEmbeddable price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameType gameType;

    public ReservationEntity(
            UUID id,
            CourtEntity court,
            CustomerEntity customer,
            LocalDateTime startTime,
            LocalDateTime endTime,
            PriceEmbeddable price,
            GameType gameType,
            boolean deleted
    ) {
        super(id, deleted);
        this.court = court;
        this.customer = customer;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.gameType = gameType;
    }
}
