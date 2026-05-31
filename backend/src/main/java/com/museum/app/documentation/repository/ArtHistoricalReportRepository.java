package com.museum.app.documentation.repository;

import com.museum.app.documentation.domain.ArtHistoricalReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtHistoricalReportRepository extends JpaRepository<ArtHistoricalReport, UUID> {

    List<ArtHistoricalReport> findByObjectIdAndDeletedFalseOrderByReportDateDesc(UUID objectId);

    Optional<ArtHistoricalReport> findByIdAndObjectIdAndDeletedFalse(UUID id, UUID objectId);
}
