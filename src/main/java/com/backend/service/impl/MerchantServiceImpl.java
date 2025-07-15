package com.backend.service.impl;

import com.backend.dto.MerchantRequestDTO;
import com.backend.dto.MerchantResponseDTO;
import com.backend.exceptions.DuplicateResourceException;
import com.backend.exceptions.ResourceNotFoundException;
import com.backend.model.Merchant;
import com.backend.repository.MerchantRepository;
import com.backend.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public MerchantResponseDTO createMerchant(MerchantRequestDTO merchantDTO) {
        // Check if merchant with same name or email already exists
        if (merchantRepository.existsByCompanyName(merchantDTO.getName())) {
            throw new DuplicateResourceException("Merchant with name '" + merchantDTO.getName() + "' already exists");
        }

        if (merchantDTO.getEmail() != null && merchantRepository.existsByEmail(merchantDTO.getEmail())) {
            throw new DuplicateResourceException("Merchant with email '" + merchantDTO.getEmail() + "' already exists");
        }

        Merchant merchant = modelMapper.map(merchantDTO, Merchant.class);
        Merchant savedMerchant = merchantRepository.save(merchant);
        return modelMapper.map(savedMerchant, MerchantResponseDTO.class);
    }

    @Override
    public MerchantResponseDTO getMerchantById(Long id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with id: " + id));
        return modelMapper.map(merchant, MerchantResponseDTO.class);
    }

    @Override
    public Page<MerchantResponseDTO> getAllMerchants(int page, int size) {
        Page<Merchant> merchantPage = merchantRepository.findAll(
                PageRequest.of(page, size, Sort.by("name").ascending())
        );
        return merchantPage.map(merchant -> modelMapper.map(merchant, MerchantResponseDTO.class));
    }

    @Override
    @Transactional
    public MerchantResponseDTO updateMerchant(Long id, MerchantRequestDTO merchantDTO) {
        Merchant existingMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with id: " + id));

        // Check if another merchant has the same name or email
        if (!existingMerchant.getCompanyName().equals(merchantDTO.getName()) &&
                merchantRepository.existsByCompanyName(merchantDTO.getName())) {
            throw new DuplicateResourceException("Merchant with name '" + merchantDTO.getName() + "' already exists");
        }

        if (merchantDTO.getEmail() != null &&
                !merchantDTO.getEmail().equals(existingMerchant.getEmail()) &&
                merchantRepository.existsByEmail(merchantDTO.getEmail())) {
            throw new DuplicateResourceException("Merchant with email '" + merchantDTO.getEmail() + "' already exists");
        }

        modelMapper.map(merchantDTO, existingMerchant);
        existingMerchant.setId(id); // Ensure ID remains the same
        Merchant updatedMerchant = merchantRepository.save(existingMerchant);
        return modelMapper.map(updatedMerchant, MerchantResponseDTO.class);
    }

    @Override
    @Transactional
    public void deleteMerchant(Long id) {
        if (!merchantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Merchant not found with id: " + id);
        }
        merchantRepository.deleteById(id);
    }
}
