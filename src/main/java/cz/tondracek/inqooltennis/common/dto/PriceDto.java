package cz.tondracek.inqooltennis.common.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceDto {
    private BigDecimal amount;
    private String currencyCode;
}
