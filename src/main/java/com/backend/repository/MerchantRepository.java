package com.backend.repository;

import com.backend.dto.MerchantResponseDTO;
import com.backend.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    List<MerchantResponseDTO> findByStatus(String status);
    boolean existsByCompanyName(String name);
    boolean existsByEmail(String email);
}
