package com.museum.app.core.repository;

import com.museum.app.core.domain.MuseumObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MuseumObjectRepository extends JpaRepository<MuseumObject, UUID> {

    Page<MuseumObject> findByDeletedFalse(Pageable pageable);

    Optional<MuseumObject> findByIdAndDeletedFalse(UUID id);

    List<MuseumObject> findByInventarNumberAndDeletedFalse(String inventarNumber);

    boolean existsByIdAndDeletedFalse(UUID id);

    @Query(value = "SELECT generate_system_number()", nativeQuery = true)
    String nextSystemNumber();
}
