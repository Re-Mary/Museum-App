package com.museum.app.documentation.repository;

import com.museum.app.documentation.domain.Restoration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestorationRepository extends JpaRepository<Restoration, UUID> {

    List<Restoration> findByObjectIdAndDeletedFalseOrderByStartDateDesc(UUID objectId);

    Optional<Restoration> findByIdAndObjectIdAndDeletedFalse(UUID id, UUID objectId);
}
