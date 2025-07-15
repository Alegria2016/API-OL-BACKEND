package com.backend.service.impl;

import com.backend.service.MerchantReportService;
import jakarta.persistence.*;
import org.hibernate.dialect.OracleTypes;
import org.hibernate.procedure.ProcedureCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

@Service
public class MerchantReportServiceImpl implements MerchantReportService {
    @PersistenceContext
    private EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;

    // Inyección por constructor
    public MerchantReportServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Map<String, Object>> getActiveMerchantsReport() {
        // 1. Usar CallableStatement para mayor control
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{ ? = call get_active_merchants_report() }")) {

                // Registrar parámetro de salida
                cs.registerOutParameter(1, OracleTypes.CURSOR);
                cs.execute();

                // Obtener el ResultSet
                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    List<Map<String, Object>> result = new ArrayList<>();
                    ResultSetMetaData meta = rs.getMetaData();

                    while (rs.next()) {
                        Map<String, Object> row = new LinkedHashMap<>();
                        row.put("merchant_name", rs.getString("merchant_name"));
                        row.put("city", rs.getString("city"));
                        row.put("phone", rs.getString("phone"));
                        row.put("email", rs.getString("email"));
                        row.put("registration_date", rs.getDate("registration_date"));
                        row.put("status", rs.getString("status"));
                        row.put("establishment_count", rs.getInt("establishment_count"));
                        row.put("total_income", rs.getBigDecimal("total_income"));
                        row.put("total_employees", rs.getInt("total_employees"));
                        result.add(row);
                    }
                    return result;
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al ejecutar procedimiento", e);
            }
        });
    }

}
