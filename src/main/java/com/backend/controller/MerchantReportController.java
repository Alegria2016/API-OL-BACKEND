package com.backend.controller;

import com.backend.service.MerchantReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reports")
public class MerchantReportController {
    private final MerchantReportService merchantReportService;

    public MerchantReportController(MerchantReportService merchantReportService) {
        this.merchantReportService = merchantReportService;
    }

    @GetMapping("/active-merchants")
    public ResponseEntity<List<Map<String, Object>>> getActiveMerchantsReport() {
        return ResponseEntity.ok(merchantReportService.getActiveMerchantsReport());
    }
}
