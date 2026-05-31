package com.museum.app.documentation.repository;

import com.museum.app.documentation.domain.Provenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProvenanceRepository extends JpaRepository<Provenance, UUID> {

    List<Provenance> findByObjectIdAndDeletedFalseOrderByFromDateDesc(UUID objectId);

    Optional<Provenance> findByIdAndObjectIdAndDeletedFalse(UUID id, UUID objectId);
}
