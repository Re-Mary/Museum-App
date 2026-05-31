package com.museum.app.core.domain;

import com.museum.app.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "museum_object")
public class MuseumObject extends BaseEntity {

    @Column(name = "system_number", length = 50)
    private String systemNumber;

    @Column(name = "inventar_number", length = 100)
    private String inventarNumber;

    @Column(name = "part_number", length = 50)
    private String partNumber;

    @Column(name = "parent_object_id")
    private UUID parentObjectId;

    @Column(nullable = false)
    private String title;

    @Column(name = "object_date_text", length = 100)
    private String objectDateText;

    @Column(name = "object_date_from")
    private LocalDate objectDateFrom;

    @Column(name = "object_date_to")
    private LocalDate objectDateTo;

    private String technique;

    @Column(name = "height_cm", precision = 10, scale = 2)
    private BigDecimal heightCm;

    @Column(name = "width_cm", precision = 10, scale = 2)
    private BigDecimal widthCm;

    @Column(name = "depth_cm", precision = 10, scale = 2)
    private BigDecimal depthCm;

    @Column(name = "owner_organization_id")
    private UUID ownerOrganizationId;

    @Column(name = "storage_location_id")
    private UUID storageLocationId;

    @Column(name = "container_id")
    private UUID containerId;

    @Column(name = "can_be_exhibited")
    private Boolean canBeExhibited;

    @Column(name = "can_be_transported")
    private Boolean canBeTransported;

    private String notes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    public String getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(String systemNumber) {
        this.systemNumber = systemNumber;
    }

    public String getInventarNumber() {
        return inventarNumber;
    }

    public void setInventarNumber(String inventarNumber) {
        this.inventarNumber = inventarNumber;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public UUID getParentObjectId() {
        return parentObjectId;
    }

    public void setParentObjectId(UUID parentObjectId) {
        this.parentObjectId = parentObjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObjectDateText() {
        return objectDateText;
    }

    public void setObjectDateText(String objectDateText) {
        this.objectDateText = objectDateText;
    }

    public LocalDate getObjectDateFrom() {
        return objectDateFrom;
    }

    public void setObjectDateFrom(LocalDate objectDateFrom) {
        this.objectDateFrom = objectDateFrom;
    }

    public LocalDate getObjectDateTo() {
        return objectDateTo;
    }

    public void setObjectDateTo(LocalDate objectDateTo) {
        this.objectDateTo = objectDateTo;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public BigDecimal getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(BigDecimal heightCm) {
        this.heightCm = heightCm;
    }

    public BigDecimal getWidthCm() {
        return widthCm;
    }

    public void setWidthCm(BigDecimal widthCm) {
        this.widthCm = widthCm;
    }

    public BigDecimal getDepthCm() {
        return depthCm;
    }

    public void setDepthCm(BigDecimal depthCm) {
        this.depthCm = depthCm;
    }

    public UUID getOwnerOrganizationId() {
        return ownerOrganizationId;
    }

    public void setOwnerOrganizationId(UUID ownerOrganizationId) {
        this.ownerOrganizationId = ownerOrganizationId;
    }

    public UUID getStorageLocationId() {
        return storageLocationId;
    }

    public void setStorageLocationId(UUID storageLocationId) {
        this.storageLocationId = storageLocationId;
    }

    public UUID getContainerId() {
        return containerId;
    }

    public void setContainerId(UUID containerId) {
        this.containerId = containerId;
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
