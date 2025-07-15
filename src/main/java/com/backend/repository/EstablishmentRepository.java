package com.backend.repository;

import com.backend.dto.EstablishmentResponseDTO;
import com.backend.model.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {



}
