package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentResponseDTO {
    private Long id;
    private String name;
    private BigDecimal income;
    private Integer employeeCount;
    private Long merchantId;
    private String merchantName;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
