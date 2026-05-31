package com.museum.app.documentation.repository;

import com.museum.app.documentation.domain.ResearchReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResearchReportRepository extends JpaRepository<ResearchReport, UUID> {

    List<ResearchReport> findByObjectIdAndDeletedFalseOrderByReportDateDesc(UUID objectId);

    Optional<ResearchReport> findByIdAndObjectIdAndDeletedFalse(UUID id, UUID objectId);
}
