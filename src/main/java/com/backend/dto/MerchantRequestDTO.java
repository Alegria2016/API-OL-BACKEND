package com.backend.dto;

import com.backend.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantRequestDTO {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Municipality is mandatory")
    @Size(max = 100, message = "Municipality must be less than 100 characters")
    private String municipality;

    @Size(max = 20, message = "Phone must be less than 20 characters")
    private String phone;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @NotNull(message = "Registration date is mandatory")
    private LocalDate registrationDate;

    @NotNull(message = "Status is mandatory")
    private Status status;
}
