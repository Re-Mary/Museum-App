package com.museum.app.documentation.domain;

import com.museum.app.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "art_historical_report")
public class ArtHistoricalReport extends BaseEntity {

    @Column(name = "object_id", nullable = false)
    private UUID objectId;

    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    private String title;

    @Column(name = "author_person_id")
    private UUID authorPersonId;

    @Column(name = "author_user_id")
    private UUID authorUserId;

    private String attribution;

    @Column(name = "attribution_certainty", length = 20)
    private String attributionCertainty;

    @Column(name = "dating_text")
    private String datingText;

    @Column(name = "dating_from")
    private LocalDate datingFrom;

    @Column(name = "dating_to")
    private LocalDate datingTo;

    @Column(name = "style_period")
    private String stylePeriod;

    @Column(name = "subject_iconography")
    private String subjectIconography;

    @Column(name = "technique_analysis")
    private String techniqueAnalysis;

    @Column(name = "historical_context")
    private String historicalContext;

    private String bibliography;

    @Column(name = "exhibition_history_summary")
    private String exhibitionHistorySummary;

    @Column(nullable = false, length = 20)
    private String status = "draft";

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber = 1;

    @Column(name = "supersedes_report_id")
    private UUID supersedesReportId;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getAuthorPersonId() {
        return authorPersonId;
    }

    public void setAuthorPersonId(UUID authorPersonId) {
        this.authorPersonId = authorPersonId;
    }

    public UUID getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(UUID authorUserId) {
        this.authorUserId = authorUserId;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getAttributionCertainty() {
        return attributionCertainty;
    }

    public void setAttributionCertainty(String attributionCertainty) {
        this.attributionCertainty = attributionCertainty;
    }

    public String getDatingText() {
        return datingText;
    }

    public void setDatingText(String datingText) {
        this.datingText = datingText;
    }

    public LocalDate getDatingFrom() {
        return datingFrom;
    }

    public void setDatingFrom(LocalDate datingFrom) {
        this.datingFrom = datingFrom;
    }

    public LocalDate getDatingTo() {
        return datingTo;
    }

    public void setDatingTo(LocalDate datingTo) {
        this.datingTo = datingTo;
    }

    public String getStylePeriod() {
        return stylePeriod;
    }

    public void setStylePeriod(String stylePeriod) {
        this.stylePeriod = stylePeriod;
    }

    public String getSubjectIconography() {
        return subjectIconography;
    }

    public void setSubjectIconography(String subjectIconography) {
        this.subjectIconography = subjectIconography;
    }

    public String getTechniqueAnalysis() {
        return techniqueAnalysis;
    }

    public void setTechniqueAnalysis(String techniqueAnalysis) {
        this.techniqueAnalysis = techniqueAnalysis;
    }

    public String getHistoricalContext() {
        return historicalContext;
    }

    public void setHistoricalContext(String historicalContext) {
        this.historicalContext = historicalContext;
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }

    public String getExhibitionHistorySummary() {
        return exhibitionHistorySummary;
    }

    public void setExhibitionHistorySummary(String exhibitionHistorySummary) {
        this.exhibitionHistorySummary = exhibitionHistorySummary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public UUID getSupersedesReportId() {
        return supersedesReportId;
    }

    public void setSupersedesReportId(UUID supersedesReportId) {
        this.supersedesReportId = supersedesReportId;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
