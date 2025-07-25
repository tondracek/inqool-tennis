package cz.tondracek.inqooltennis.common.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
public class Price {
    private BigDecimal amount;
    private Currency currencyCode;
}