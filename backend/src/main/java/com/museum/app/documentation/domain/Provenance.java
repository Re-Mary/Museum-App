package com.museum.app.documentation.domain;

import com.museum.app.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "provenance")
public class Provenance extends BaseEntity {

    @Column(name = "object_id", nullable = false)
    private UUID objectId;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_person_id")
    private UUID ownerPersonId;

    @Column(name = "owner_organization_id")
    private UUID ownerOrganizationId;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    private String origin;

    @Column(name = "acquisition_type", length = 50)
    private String acquisitionType;

    private String notes;

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public UUID getOwnerPersonId() {
        return ownerPersonId;
    }

    public void setOwnerPersonId(UUID ownerPersonId) {
        this.ownerPersonId = ownerPersonId;
    }

    public UUID getOwnerOrganizationId() {
        return ownerOrganizationId;
    }

    public void setOwnerOrganizationId(UUID ownerOrganizationId) {
        this.ownerOrganizationId = ownerOrganizationId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAcquisitionType() {
        return acquisitionType;
    }

    public void setAcquisitionType(String acquisitionType) {
        this.acquisitionType = acquisitionType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
