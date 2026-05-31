package com.museum.app.documentation.repository;

import com.museum.app.documentation.domain.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InsuranceRepository extends JpaRepository<Insurance, UUID> {

    List<Insurance> findByObjectIdAndDeletedFalseOrderByStartDateDesc(UUID objectId);

    Optional<Insurance> findByIdAndObjectIdAndDeletedFalse(UUID id, UUID objectId);
}
