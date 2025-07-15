package com.backend.service;

import java.util.List;
import java.util.Map;

public interface MerchantReportService {

    List<Map<String, Object>> getActiveMerchantsReport();
}
