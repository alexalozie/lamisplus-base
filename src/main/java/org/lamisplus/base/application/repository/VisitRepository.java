package org.lamisplus.base.application.repository;


import org.lamisplus.base.application.domiain.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findByPatientId(Long patientId);
    Optional<Visit> findByPatientIdAndDateVisitStart(Long patientId, LocalDate dateVisitStart);
    List<Visit> findByDateVisitStart(LocalDate dateVisitStart);

    Optional<Visit> findById(Long id);

    void deleteById(Long id);
}
