package com.backend.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagedMerchantResponseDTO<T> {
    private List<T> content;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int itemsPerPage;

    public void PagedResponseDTO(Page<T> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalItems = page.getTotalElements();
        this.itemsPerPage = page.getSize();
    }
}
