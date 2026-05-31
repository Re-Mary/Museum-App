package com.museum.app.documentation.domain;

import com.museum.app.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "object_document")
public class ObjectDocument extends BaseEntity {

    @Column(name = "object_id", nullable = false)
    private UUID objectId;

    @Column(name = "linked_entity_type", nullable = false, length = 50)
    private String linkedEntityType;

    @Column(name = "linked_entity_id", nullable = false)
    private UUID linkedEntityId;

    @Column(name = "document_kind", nullable = false, length = 50)
    private String documentKind;

    private String title;

    @Column(name = "file_uri", nullable = false)
    private String fileUri;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Column(name = "file_size_bytes")
    private Long fileSizeBytes;

    @Column(name = "checksum_sha256", length = 64)
    private String checksumSha256;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt = Instant.now();

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public String getLinkedEntityType() {
        return linkedEntityType;
    }

    public void setLinkedEntityType(String linkedEntityType) {
        this.linkedEntityType = linkedEntityType;
    }

    public UUID getLinkedEntityId() {
        return linkedEntityId;
    }

    public void setLinkedEntityId(UUID linkedEntityId) {
        this.linkedEntityId = linkedEntityId;
    }

    public String getDocumentKind() {
        return documentKind;
    }

    public void setDocumentKind(String documentKind) {
        this.documentKind = documentKind;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(Long fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    public String getChecksumSha256() {
        return checksumSha256;
    }

    public void setChecksumSha256(String checksumSha256) {
        this.checksumSha256 = checksumSha256;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
