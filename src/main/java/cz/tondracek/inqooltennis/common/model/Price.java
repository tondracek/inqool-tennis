package cz.tondracek.inqooltennis.common.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Price {
    private BigDecimal amount;
    private String currencyCode;
}