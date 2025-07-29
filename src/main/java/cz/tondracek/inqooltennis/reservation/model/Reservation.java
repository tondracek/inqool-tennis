package cz.tondracek.inqooltennis.reservation.model;

import cz.tondracek.inqooltennis.common.SoftDeletable;
import cz.tondracek.inqooltennis.common.gametype.model.GameType;
import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.customer.model.Customer;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class Reservation implements SoftDeletable<Reservation> {
    UUID id;
    Court court;
    Customer customer;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Price price;
    GameType gameType;

    boolean deleted;
    LocalDateTime createdAt;

    @Override
    public Reservation withDeleted(boolean deletedValue) {
        return new Reservation(
                this.id,
                this.court,
                this.customer,
                this.startTime,
                this.endTime,
                this.price,
                this.gameType,
                deletedValue,
                this.createdAt
        );
    }
}
