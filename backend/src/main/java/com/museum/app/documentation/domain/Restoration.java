package com.museum.app.documentation.domain;

import com.museum.app.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "restoration")
public class Restoration extends BaseEntity {

    @Column(name = "object_id", nullable = false)
    private UUID objectId;

    @Column(name = "restaurator_user_id")
    private UUID restauratorUserId;

    @Column(name = "restaurator_person_id")
    private UUID restauratorPersonId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RestorationStatus status = RestorationStatus.draft;

    private String measures;

    @Column(name = "treatment_plan")
    private String treatmentPlan;

    @Column(name = "materials_used")
    private String materialsUsed;

    @Column(name = "reversibility_notes")
    private String reversibilityNotes;

    @Column(name = "ethical_assessment")
    private String ethicalAssessment;

    @Column(name = "report_summary")
    private String reportSummary;

    @Column(precision = 12, scale = 2)
    private BigDecimal cost;

    @Column(length = 3)
    private String currency;

    @Column(name = "condition_report_before_id")
    private UUID conditionReportBeforeId;

    @Column(name = "condition_report_after_id")
    private UUID conditionReportAfterId;

    private String notes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public UUID getRestauratorUserId() {
        return restauratorUserId;
    }

    public void setRestauratorUserId(UUID restauratorUserId) {
        this.restauratorUserId = restauratorUserId;
    }

    public UUID getRestauratorPersonId() {
        return restauratorPersonId;
    }

    public void setRestauratorPersonId(UUID restauratorPersonId) {
        this.restauratorPersonId = restauratorPersonId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public RestorationStatus getStatus() {
        return status;
    }

    public void setStatus(RestorationStatus status) {
        this.status = status;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public String getMaterialsUsed() {
        return materialsUsed;
    }

    public void setMaterialsUsed(String materialsUsed) {
        this.materialsUsed = materialsUsed;
    }

    public String getReversibilityNotes() {
        return reversibilityNotes;
    }

    public void setReversibilityNotes(String reversibilityNotes) {
        this.reversibilityNotes = reversibilityNotes;
    }

    public String getEthicalAssessment() {
        return ethicalAssessment;
    }

    public void setEthicalAssessment(String ethicalAssessment) {
        this.ethicalAssessment = ethicalAssessment;
    }

    public String getReportSummary() {
        return reportSummary;
    }

    public void setReportSummary(String reportSummary) {
        this.reportSummary = reportSummary;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UUID getConditionReportBeforeId() {
        return conditionReportBeforeId;
    }

    public void setConditionReportBeforeId(UUID conditionReportBeforeId) {
        this.conditionReportBeforeId = conditionReportBeforeId;
    }

    public UUID getConditionReportAfterId() {
        return conditionReportAfterId;
    }

    public void setConditionReportAfterId(UUID conditionReportAfterId) {
        this.conditionReportAfterId = conditionReportAfterId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
