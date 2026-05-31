package com.museum.app.core.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public final class MuseumObjectDtos {

    private MuseumObjectDtos() {
    }

    public record MuseumObjectRequest(
            String systemNumber,
            String inventarNumber,
            String partNumber,
            UUID parentObjectId,
            @NotBlank String title,
            String objectDateText,
            LocalDate objectDateFrom,
            LocalDate objectDateTo,
            String technique,
            BigDecimal heightCm,
            BigDecimal widthCm,
            BigDecimal depthCm,
            UUID ownerOrganizationId,
            UUID storageLocationId,
            UUID containerId,
            Boolean canBeExhibited,
            Boolean canBeTransported,
            String notes,
            Map<String, Object> metadata,
            Boolean assignSystemNumber
    ) {
    }

    public record MuseumObjectResponse(
            UUID id,
            String systemNumber,
            String inventarNumber,
            String partNumber,
            UUID parentObjectId,
            String title,
            String objectDateText,
            LocalDate objectDateFrom,
            LocalDate objectDateTo,
            String technique,
            BigDecimal heightCm,
            BigDecimal widthCm,
            BigDecimal depthCm,
            UUID ownerOrganizationId,
            UUID storageLocationId,
            UUID containerId,
            Boolean canBeExhibited,
            Boolean canBeTransported,
            String notes,
            Map<String, Object> metadata,
            Long version,
            Instant createdAt,
            Instant updatedAt
    ) {
    }
}
