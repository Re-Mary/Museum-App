package com.museum.app.documentation.repository;

import com.museum.app.documentation.domain.ConditionReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConditionReportRepository extends JpaRepository<ConditionReport, UUID> {

    List<ConditionReport> findByObjectIdAndDeletedFalseOrderByReportDateDesc(UUID objectId);

    Optional<ConditionReport> findByIdAndObjectIdAndDeletedFalse(UUID id, UUID objectId);
}
