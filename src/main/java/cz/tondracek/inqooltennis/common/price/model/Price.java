package cz.tondracek.inqooltennis.common.price.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Price {
    BigDecimal amount;
    String currencyCode;
}