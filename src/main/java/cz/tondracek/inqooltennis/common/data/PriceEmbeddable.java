package cz.tondracek.inqooltennis.common.data;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceEmbeddable {
    BigDecimal amount;
    String currencyCode;
}
