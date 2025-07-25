package cz.tondracek.inqooltennis.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PriceDto {
    @PositiveOrZero
    BigDecimal amount;
    @NotBlank
    String currencyCode;
}
