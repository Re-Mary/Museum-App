package com.museum.app.documentation.repository;

import com.museum.app.documentation.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findByObjectIdAndDeletedFalseOrderByStartDateDesc(UUID objectId);

    Optional<Loan> findByIdAndObjectIdAndDeletedFalse(UUID id, UUID objectId);
}
