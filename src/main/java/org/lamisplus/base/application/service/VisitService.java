package org.lamisplus.base.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.lamisplus.base.application.controller.RecordNotFoundException;
import org.lamisplus.base.application.domiain.dto.BadRequestAlertException;
import org.lamisplus.base.application.domiain.dto.PatientDTO;
import org.lamisplus.base.application.domiain.dto.VisitDTO;
import org.lamisplus.base.application.domiain.mapper.PatientMapper;
import org.lamisplus.base.application.domiain.mapper.VisitMapper;
import org.lamisplus.base.application.domiain.model.Patient;
import org.lamisplus.base.application.domiain.model.Person;
import org.lamisplus.base.application.domiain.model.PersonContact;
import org.lamisplus.base.application.domiain.model.Visit;
import org.lamisplus.base.application.repository.PatientRepository;
import org.lamisplus.base.application.repository.PersonContactRepository;
import org.lamisplus.base.application.repository.PersonRepository;
import org.lamisplus.base.application.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class VisitService {

    private static final String ENTITY_NAME = "visit";

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final PersonRepository personRepository;
    private final PersonContactRepository personContactRepository;
    private final VisitMapper visitMapper;
    private final PatientMapper patientMapper;


    private static Object exist(Visit o) {
        throw new BadRequestAlertException("Visit Error", ENTITY_NAME, "Already Exist");
    }

    public Visit save(VisitDTO visitDTO) {
        Optional<Visit> visit1 = this.visitRepository.findByPatientIdAndDateVisitStart(visitDTO.getPatientId(), visitDTO.getDateVisitStart());
        visit1.map(VisitService::exist);

        Visit visit = visitMapper.toVisit(visitDTO);
        Patient patient = patientRepository.getOne(visitDTO.getPatientId());
        visit.setPatient(patient);
        this.visitRepository.save(visit);
        return visit;
    }

    public List<PatientDTO> getVisitByDateStart(){
        // Converting 'dd-MMM-yyyy' String format to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<Visit> visits = this.visitRepository.findByDateVisitStart(LocalDate.parse(formatter.format(LocalDate.now()), formatter));
        LOG.info("Just Checking");
        if(visits.size() < 1){
            System.out.println("It is null....");
            throw new RecordNotFoundException();

        }
        System.out.println("We got here....");

        List<PatientDTO> patientDTOs = new ArrayList<>();

        visits.forEach(visit -> {
            Patient patient = patientRepository.getOne(visit.getPatient().getId());
            Person person = personRepository.getOne(patient.getPersonId());
            PersonContact personContact = personContactRepository.getOne(person.getId());

            PatientDTO patientDTO = patientMapper.toPatientDTO(person, personContact, patient);
            patientDTO.setDateVisitStart(visit.getDateVisitStart());
            patientDTO.setDateVisitEnd(visit.getDateVisitEnd());

            patientDTOs.add(patientDTO);
        });
        return patientDTOs;

    }
}