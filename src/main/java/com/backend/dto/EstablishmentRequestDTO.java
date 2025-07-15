package com.backend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentRequestDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotNull(message = "Income is mandatory")
    @DecimalMin(value = "0.00", inclusive = false, message = "Income must be greater than 0")
    @Digits(integer = 13, fraction = 2, message = "Income must have up to 13 integer digits and 2 decimal places")
    private BigDecimal income;

    @NotNull(message = "Employee count is mandatory")
    @Min(value = 1, message = "Employee count must be at least 1")
    private Integer employeeCount;

    @NotNull(message = "Merchant ID is mandatory")
    private Long merchantId;
}
