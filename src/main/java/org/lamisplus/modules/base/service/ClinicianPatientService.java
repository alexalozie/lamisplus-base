package org.lamisplus.modules.base.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.ClinicianPatientDTO;
import org.lamisplus.modules.base.domain.entity.ApplicationCodeset;
import org.lamisplus.modules.base.domain.entity.ClinicianPatient;
import org.lamisplus.modules.base.domain.entity.Patient;
import org.lamisplus.modules.base.domain.entity.Visit;
import org.lamisplus.modules.base.domain.mapper.ClinicianPatientMapper;
import org.lamisplus.modules.base.repository.ApplicationCodesetRepository;
import org.lamisplus.modules.base.repository.ClinicianPatientRepository;
import org.lamisplus.modules.base.repository.PatientRepository;
import org.lamisplus.modules.base.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ClinicianPatientService {

    private final ClinicianPatientRepository clinicianPatientRepository;
    private final ClinicianPatientMapper clinicianPatientMapper;
    private final VisitRepository visitRepository;
    private final ApplicationCodesetRepository applicationCodesetRepository;
    private final PatientRepository patientRepository;

    private static Object exist(ClinicianPatient clinicianPatient) {
        throw new RecordExistException(ClinicianPatient.class, "Clinician", "already assigned to Patient");
    }

    public ClinicianPatient assignClinician(ClinicianPatientDTO clinicianPatientDTO) {
        log.info("Saving Assigned Clinician to Patient " + clinicianPatientDTO.getPatientId() + "visitid = " + clinicianPatientDTO.getVisitId() + clinicianPatientDTO.getClinicianId() +
                clinicianPatientDTO.getApplicationCodesetId());

        Optional<ClinicianPatient> clinicianPatient1 = this.clinicianPatientRepository.findByClinicianIdAndPatientIdAndVisitId(clinicianPatientDTO.getClinicianId(),
                clinicianPatientDTO.getPatientId(), clinicianPatientDTO.getVisitId());

        Optional <Visit> visit = this.visitRepository.findById(clinicianPatientDTO.getApplicationCodesetId());
        Optional<ApplicationCodeset> applicationCodeset = this.applicationCodesetRepository.findById(clinicianPatientDTO.getApplicationCodesetId());
        Optional<Patient> patient = this.patientRepository.findById(clinicianPatientDTO.getPatientId());

        if(!visit.isPresent() || !applicationCodeset.isPresent() || !patient.isPresent()){
            throw new EntityNotFoundException(ClinicianPatient.class, "Clinician Id", clinicianPatientDTO.getClinicianId() +
                    ", Patient Id=" + clinicianPatientDTO.getPatientId() +", Visit Id=" + clinicianPatientDTO.getVisitId());
        }
        if(clinicianPatient1.isPresent()) {
            throw new RecordExistException(ClinicianPatient.class, "Clinician Id", clinicianPatientDTO.getClinicianId() +
                    ", Patient Id=" + clinicianPatientDTO.getPatientId() +", Visit Id=" + clinicianPatientDTO.getVisitId());
        }
        final ClinicianPatient clinicianPatient = clinicianPatientMapper.toClinicianPatient(clinicianPatientDTO);
        this.clinicianPatientRepository.save(clinicianPatient);

        log.info("Saving Assigned Clinician to Patient " + clinicianPatient);

        return clinicianPatient;

    }
}
