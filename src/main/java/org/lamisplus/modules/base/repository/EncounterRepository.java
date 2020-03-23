package org.lamisplus.modules.base.repository;

import org.lamisplus.modules.base.domain.entities.Encounter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

//EncounterRepository
public interface EncounterRepository extends JpaRepository<Encounter, Long> , JpaSpecificationExecutor {
    //Encounter
    Optional<Encounter> findByPatientIdAndServiceNameAndFormNameAndVisitId(Long patientId, String serviceName, String formName, Long visitId);

    Optional<Encounter> findByPatientIdAndServiceNameAndFormNameAndDateEncounter(Long patientId, String serviceName, String formName, LocalDate dateFncounter);

    List<Encounter> findBypatientId(Long PatientId);

    //List<PatientObservation> findByPatientAndFormCodeTitle(Patient patient, Long formCode, String title);
    Optional<Encounter> findFirstByPatientIdAndServiceNameAndFormNameOrderByDateEncounterDesc(Long patientId, String serviceName, String formName);

    //Optional<Encounter> findByMaxDAndDateEncounter(String formName, Long patientId);
    List<Encounter> findAllByPatientIdAndServiceNameAndFormNameOrderByDateEncounterDesc(Long patientId, String serviceName, String formName);

    List<Encounter> findAllByServiceNameAndFormNameAndDateEncounterOrderByDateEncounterDesc(String serviceName, String formName, LocalDate DateEncounter);

    Optional <Encounter> findTopByServiceNameAndFormNameAndPatientIdOrderByDateEncounterAsc(String serviceName, String formName, Long patientId);

    Optional <Encounter> findTopByServiceNameAndFormNameAndPatientIdOrderByDateEncounterDesc(String serviceName, String formName, Long patientId);

    List<Encounter> findAllByPatientIdAndServiceNameAndFormNameAndDateEncounterBetween(Long patientId, String serviceName, String formName, LocalDate firstDate, LocalDate endDate);

    @Query("select e from Encounter e where e.serviceName=?1 and e.formName=?2 " +
            "and e.dateEncounter >= ?3 and e.dateEncounter <= ?4")
    List<Encounter> findAllByServiceNameAndFormNameAndDateEncounterIsBetweenQuery(String serviceName, String formName, LocalDate dateStart, LocalDate dateEnd);

/*    @Query("select e from Encounter e where e.patientId=?1 and e.serviceName=?2 and e.formName=?3 " +
            "and e.dateEncounter >= ?4 and e.dateEncounter <= ?5 order by ?6, LIMIT = ?7")*/
    List <Encounter> findAllByPatientIdAndServiceNameAndFormName(Long patientId, String serviceName, String formName, Pageable pageable);

    Optional<Encounter> findFirstByPatientIdAndServiceNameAndFormNameAndVisitIdOrderByDateEncounterDesc(Long patientId, String serviceName,String formName, Long visitId);


}

