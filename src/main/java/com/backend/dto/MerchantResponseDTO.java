package com.backend.dto;

import com.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantResponseDTO {
    private Long id;
    private String companyName;
    private String state;
    private String phone;
    private String email;
    private LocalDate registrationDate;
    private Status status;
}
