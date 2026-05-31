package com.museum.app.documentation.dto;

import com.museum.app.documentation.domain.ConditionCode;
import com.museum.app.documentation.domain.ResearchType;
import com.museum.app.documentation.domain.RestorationStatus;
import com.museum.app.documentation.domain.SeverityLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public final class DocumentationDtos {

    private DocumentationDtos() {
    }

    public record ConditionReportRequest(
            @NotNull LocalDate reportDate,
            UUID createdByUserId,
            UUID conservatorPersonId,
            @NotNull ConditionCode conditionCode,
            SeverityLevel damageSeverity,
            String summary,
            String recommendations,
            Boolean canBeExhibited,
            Boolean canBeTransported
    ) {
    }

    public record ConditionReportResponse(
            UUID id,
            UUID objectId,
            LocalDate reportDate,
            ConditionCode conditionCode,
            SeverityLevel damageSeverity,
            String summary,
            String recommendations,
            Boolean canBeExhibited,
            Boolean canBeTransported,
            Long version,
            Instant createdAt
    ) {
    }

    public record ProvenanceRequest(
            String ownerName,
            UUID ownerPersonId,
            UUID ownerOrganizationId,
            LocalDate fromDate,
            LocalDate toDate,
            String origin,
            String acquisitionType,
            String notes
    ) {
    }

    public record ProvenanceResponse(
            UUID id,
            UUID objectId,
            String ownerName,
            LocalDate fromDate,
            LocalDate toDate,
            String origin,
            String acquisitionType,
            String notes,
            Long version
    ) {
    }

    public record InsuranceRequest(
            String insurer,
            BigDecimal insuredValue,
            String currency,
            String contractNumber,
            LocalDate startDate,
            LocalDate endDate,
            String specialConditions
    ) {
    }

    public record InsuranceResponse(
            UUID id,
            UUID objectId,
            String insurer,
            BigDecimal insuredValue,
            String currency,
            String contractNumber,
            LocalDate startDate,
            LocalDate endDate,
            Long version
    ) {
    }

    public record RestorationRequest(
            UUID restauratorUserId,
            UUID restauratorPersonId,
            LocalDate startDate,
            LocalDate endDate,
            RestorationStatus status,
            String measures,
            String treatmentPlan,
            String materialsUsed,
            String reversibilityNotes,
            String ethicalAssessment,
            String reportSummary,
            BigDecimal cost,
            String currency,
            UUID conditionReportBeforeId,
            UUID conditionReportAfterId,
            String notes,
            Map<String, Object> metadata
    ) {
    }

    public record RestorationResponse(
            UUID id,
            UUID objectId,
            RestorationStatus status,
            LocalDate startDate,
            LocalDate endDate,
            String treatmentPlan,
            String reportSummary,
            UUID conditionReportBeforeId,
            UUID conditionReportAfterId,
            Long version
    ) {
    }

    public record LoanRequest(
            @NotBlank String institution,
            UUID institutionOrganizationId,
            @NotBlank String loanType,
            LocalDate startDate,
            LocalDate endDate,
            String insuranceNotes,
            String conditionBefore,
            String conditionAfter,
            UUID conditionReportBeforeId,
            UUID conditionReportAfterId,
            String notes
    ) {
    }

    public record LoanResponse(
            UUID id,
            UUID objectId,
            String institution,
            String loanType,
            LocalDate startDate,
            LocalDate endDate,
            Long version
    ) {
    }

    public record ResearchReportRequest(
            @NotNull LocalDate reportDate,
            @NotNull ResearchType researchType,
            String title,
            String institution,
            String labName,
            UUID researcherPersonId,
            UUID researcherUserId,
            String methodology,
            String sampleDescription,
            String results,
            String conclusions,
            String status,
            Map<String, Object> metadata
    ) {
    }

    public record ResearchReportResponse(
            UUID id,
            UUID objectId,
            LocalDate reportDate,
            ResearchType researchType,
            String title,
            String results,
            String conclusions,
            String status,
            Long version
    ) {
    }

    public record ArtHistoricalReportRequest(
            @NotNull LocalDate reportDate,
            String title,
            UUID authorPersonId,
            UUID authorUserId,
            String attribution,
            String attributionCertainty,
            String datingText,
            LocalDate datingFrom,
            LocalDate datingTo,
            String stylePeriod,
            String subjectIconography,
            String techniqueAnalysis,
            String historicalContext,
            String bibliography,
            String exhibitionHistorySummary,
            String status,
            Integer versionNumber,
            UUID supersedesReportId,
            Map<String, Object> metadata
    ) {
    }

    public record ArtHistoricalReportResponse(
            UUID id,
            UUID objectId,
            LocalDate reportDate,
            String title,
            String attribution,
            String attributionCertainty,
            String datingText,
            String stylePeriod,
            String status,
            Integer versionNumber,
            UUID supersedesReportId,
            Long version
    ) {
    }

    public record ObjectDocumentRequest(
            @NotBlank String linkedEntityType,
            @NotNull UUID linkedEntityId,
            @NotBlank String documentKind,
            String title,
            @NotBlank String fileUri,
            String mimeType,
            Long fileSizeBytes,
            String checksumSha256
    ) {
    }

    public record ObjectDocumentResponse(
            UUID id,
            UUID objectId,
            String linkedEntityType,
            UUID linkedEntityId,
            String documentKind,
            String title,
            String fileUri,
            String mimeType,
            Instant uploadedAt,
            Long version
    ) {
    }
}
