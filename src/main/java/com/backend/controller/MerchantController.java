package com.backend.controller;

import com.backend.dto.MerchantRequestDTO;
import com.backend.dto.MerchantResponseDTO;
import com.backend.service.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/merchants")

@Tag(name = "Merchants", description = "API for managing merchants")
public class MerchantController {
    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping
    @Operation(summary = "Create a new merchant")
    public ResponseEntity<MerchantResponseDTO> createMerchant(@Valid @RequestBody MerchantRequestDTO merchantDTO) {
        MerchantResponseDTO createdMerchant = merchantService.createMerchant(merchantDTO);
        return new ResponseEntity<>(createdMerchant, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get merchant by ID")
    public ResponseEntity<MerchantResponseDTO> getMerchantById(@PathVariable Long id) {
        MerchantResponseDTO merchant = merchantService.getMerchantById(id);
        return ResponseEntity.ok(merchant);
    }

    @GetMapping
    @Operation(summary = "Get all merchants with pagination")
    public ResponseEntity<Page<MerchantResponseDTO>> getAllMerchants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<MerchantResponseDTO> merchants = merchantService.getAllMerchants(page, size);
        return ResponseEntity.ok(merchants);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update merchant by ID")
    public ResponseEntity<MerchantResponseDTO> updateMerchant(
            @PathVariable Long id,
            @Valid @RequestBody MerchantRequestDTO merchantDTO) {
        MerchantResponseDTO updatedMerchant = merchantService.updateMerchant(id, merchantDTO);
        return ResponseEntity.ok(updatedMerchant);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete merchant by ID")
    public ResponseEntity<Void> deleteMerchant(@PathVariable Long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.noContent().build();
    }
}
