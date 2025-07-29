package cz.tondracek.inqooltennis.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerDto(
        @NotBlank String name,
        @NotBlank String phoneNumber
) {
}
