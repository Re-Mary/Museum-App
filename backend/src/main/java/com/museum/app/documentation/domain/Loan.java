package com.museum.app.documentation.domain;

import com.museum.app.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "loan")
public class Loan extends BaseEntity {

    @Column(name = "object_id", nullable = false)
    private UUID objectId;

    @Column(nullable = false)
    private String institution;

    @Column(name = "institution_organization_id")
    private UUID institutionOrganizationId;

    @Column(name = "loan_type", nullable = false, length = 20)
    private String loanType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "insurance_notes")
    private String insuranceNotes;

    @Column(name = "condition_before", length = 20)
    private String conditionBefore;

    @Column(name = "condition_after", length = 20)
    private String conditionAfter;

    @Column(name = "condition_report_before_id")
    private UUID conditionReportBeforeId;

    @Column(name = "condition_report_after_id")
    private UUID conditionReportAfterId;

    private String notes;

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public UUID getInstitutionOrganizationId() {
        return institutionOrganizationId;
    }

    public void setInstitutionOrganizationId(UUID institutionOrganizationId) {
        this.institutionOrganizationId = institutionOrganizationId;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
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

    public String getInsuranceNotes() {
        return insuranceNotes;
    }

    public void setInsuranceNotes(String insuranceNotes) {
        this.insuranceNotes = insuranceNotes;
    }

    public String getConditionBefore() {
        return conditionBefore;
    }

    public void setConditionBefore(String conditionBefore) {
        this.conditionBefore = conditionBefore;
    }

    public String getConditionAfter() {
        return conditionAfter;
    }

    public void setConditionAfter(String conditionAfter) {
        this.conditionAfter = conditionAfter;
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
}
