package com.backend.service;

import com.backend.dto.MerchantRequestDTO;
import com.backend.dto.MerchantResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MerchantService {
    MerchantResponseDTO createMerchant(MerchantRequestDTO merchantRequestDTO);
    MerchantResponseDTO getMerchantById(Long id);
    Page<MerchantResponseDTO> getAllMerchants(int page, int size);
    MerchantResponseDTO updateMerchant(Long id, MerchantRequestDTO merchantDTO);
    void deleteMerchant(Long id);
}
