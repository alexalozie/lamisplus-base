package org.lamisplus.base.application.repository;

import org.lamisplus.base.application.domiain.model.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {
    //Encounter
    Optional<Encounter> findByPatientIdAndServiceNameAndFormNameAndVisitId(Long patientId, String serviceName, String formName, Long visitId);
    Optional<Encounter> findByPatientIdAndServiceNameAndFormNameAndDateEncounter(Long patientId, String serviceName, String formName, LocalDate encounterDate);
    List<Encounter> findBypatientId(Long PatientId);

    Optional<Encounter> findById(Long id);

    Optional<Encounter> findFirstByPatientIdAndServiceNameAndFormNameOrderByDateEncounterDesc(Long patientId, String serviceName, String formName);

    List<Encounter> findAllByServiceNameAndFormNameAndDateEncounterOrderByDateEncounterDesc(String serviceName, String formName, LocalDate dateEncounter);

//    Optional<Encounter> findByPatientIdAndServiceNameAndFormNameAndDateEncounter(Long patientId, String serviceName, String formName, LocalDate localDate);
    //List<PatientObservation> findByPatientAndFormCodeTitle(Patient patient, Long formCode, String title);
}

