package com.museum.app.documentation.repository;

import com.museum.app.documentation.domain.ObjectDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ObjectDocumentRepository extends JpaRepository<ObjectDocument, UUID> {

    List<ObjectDocument> findByObjectIdAndDeletedFalseOrderByUploadedAtDesc(UUID objectId);

    List<ObjectDocument> findByObjectIdAndLinkedEntityTypeAndLinkedEntityIdAndDeletedFalse(
            UUID objectId, String linkedEntityType, UUID linkedEntityId);

    Optional<ObjectDocument> findByIdAndObjectIdAndDeletedFalse(UUID id, UUID objectId);
}
