package cz.tondracek.inqooltennis.common.model;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Price {
    BigDecimal amount;
    String currencyCode;
}