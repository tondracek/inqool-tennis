package cz.tondracek.inqooltennis.common.price;

import cz.tondracek.inqooltennis.common.price.data.PriceEmbeddable;
import cz.tondracek.inqooltennis.common.price.dto.PriceDto;
import cz.tondracek.inqooltennis.common.price.model.Price;

import java.math.BigDecimal;

public class PriceSample {

    public static final Price EUR_100 = new Price(BigDecimal.valueOf(100f), "EUR");
    public static final PriceDto EUR_100_DTO = new PriceDto(BigDecimal.valueOf(100f), "EUR");
    public static final PriceEmbeddable EUR_100_EMBEDDABLE = new PriceEmbeddable(BigDecimal.valueOf(100f), "EUR");

    public static final Price EUR_200 = new Price(BigDecimal.valueOf(200f), "EUR");
    public static final PriceDto EUR_200_DTO = new PriceDto(BigDecimal.valueOf(200f), "EUR");
    public static final PriceEmbeddable EUR_200_EMBEDDABLE = new PriceEmbeddable(BigDecimal.valueOf(200f), "EUR");

    public static final Price CZK_100 = new Price(BigDecimal.valueOf(100f), "CZK");
    public static final PriceDto CZK_100_DTO = new PriceDto(BigDecimal.valueOf(100f), "CZK");
    public static final PriceEmbeddable CZK_100_EMBEDDABLE = new PriceEmbeddable(BigDecimal.valueOf(100f), "CZK");
}
