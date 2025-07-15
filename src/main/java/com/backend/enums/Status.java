package com.backend.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    PENDING("Pending Approval"),
    SUSPENDED("Suspended"),
    DELETED("Deleted");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // Optional: Convert from string to enum
    public static Status fromDescription(String description) {
        for (Status status : Status.values()) {
            if (status.description.equalsIgnoreCase(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No status found for: " + description);
    }

    // Optional: Get all status values as list
    public static List<String> getAllDescriptions() {
        return Arrays.stream(Status.values())
                .map(Status::getDescription)
                .collect(Collectors.toList());
    }
}
