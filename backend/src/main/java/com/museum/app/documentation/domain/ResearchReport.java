package com.museum.app.documentation.domain;

import com.museum.app.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "research_report")
public class ResearchReport extends BaseEntity {

    @Column(name = "object_id", nullable = false)
    private UUID objectId;

    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "research_type", nullable = false, length = 50)
    private ResearchType researchType;

    private String title;
    private String institution;

    @Column(name = "lab_name")
    private String labName;

    @Column(name = "researcher_person_id")
    private UUID researcherPersonId;

    @Column(name = "researcher_user_id")
    private UUID researcherUserId;

    private String methodology;

    @Column(name = "sample_description")
    private String sampleDescription;

    private String results;
    private String conclusions;

    @Column(nullable = false, length = 20)
    private String status = "draft";

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;

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

    public ResearchType getResearchType() {
        return researchType;
    }

    public void setResearchType(ResearchType researchType) {
        this.researchType = researchType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public UUID getResearcherPersonId() {
        return researcherPersonId;
    }

    public void setResearcherPersonId(UUID researcherPersonId) {
        this.researcherPersonId = researcherPersonId;
    }

    public UUID getResearcherUserId() {
        return researcherUserId;
    }

    public void setResearcherUserId(UUID researcherUserId) {
        this.researcherUserId = researcherUserId;
    }

    public String getMethodology() {
        return methodology;
    }

    public void setMethodology(String methodology) {
        this.methodology = methodology;
    }

    public String getSampleDescription() {
        return sampleDescription;
    }

    public void setSampleDescription(String sampleDescription) {
        this.sampleDescription = sampleDescription;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getConclusions() {
        return conclusions;
    }

    public void setConclusions(String conclusions) {
        this.conclusions = conclusions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
