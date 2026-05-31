package com.museum.app.documentation.domain;

import com.museum.app.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "condition_report")
public class ConditionReport extends BaseEntity {

    @Column(name = "object_id", nullable = false)
    private UUID objectId;

    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    @Column(name = "created_by_user_id")
    private UUID createdByUserId;

    @Column(name = "conservator_person_id")
    private UUID conservatorPersonId;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_code", nullable = false, length = 20)
    private ConditionCode conditionCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "damage_severity", length = 20)
    private SeverityLevel damageSeverity;

    private String summary;
    private String recommendations;

    @Column(name = "can_be_exhibited")
    private Boolean canBeExhibited;

    @Column(name = "can_be_transported")
    private Boolean canBeTransported;

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public UUID getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(UUID createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public UUID getConservatorPersonId() {
        return conservatorPersonId;
    }

    public void setConservatorPersonId(UUID conservatorPersonId) {
        this.conservatorPersonId = conservatorPersonId;
    }

    public ConditionCode getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(ConditionCode conditionCode) {
        this.conditionCode = conditionCode;
    }

    public SeverityLevel getDamageSeverity() {
        return damageSeverity;
    }

    public void setDamageSeverity(SeverityLevel damageSeverity) {
        this.damageSeverity = damageSeverity;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public Boolean getCanBeExhibited() {
        return canBeExhibited;
    }

    public void setCanBeExhibited(Boolean canBeExhibited) {
        this.canBeExhibited = canBeExhibited;
    }

    public Boolean getCanBeTransported() {
        return canBeTransported;
    }

    public void setCanBeTransported(Boolean canBeTransported) {
        this.canBeTransported = canBeTransported;
    }
}
