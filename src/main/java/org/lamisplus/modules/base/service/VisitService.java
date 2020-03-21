package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.PatientDTO;
import org.lamisplus.modules.base.domain.dto.VisitDTO;
import org.lamisplus.modules.base.domain.entities.Patient;
import org.lamisplus.modules.base.domain.entities.Person;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.entities.PersonContact;
import org.lamisplus.modules.base.domain.entities.Visit;
import org.lamisplus.modules.base.domain.mapper.PatientMapper;
import org.lamisplus.modules.base.domain.mapper.VisitMapper;
import org.lamisplus.modules.base.repository.*;
import org.lamisplus.modules.base.controller.RecordNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
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

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final PersonRepository personRepository;
    private final VisitMapper visitMapper;

    private static Object exist(Visit o) {
        throw new RecordExistException(Visit.class, "Id", o.getId()+ ", Date Visit Start=" + o.getDateVisitStart());
    }

    //Saving a Visit/ Checking in a patient
    public Visit save(VisitDTO visitDTO) {
        Optional<Visit> visit1 = this.visitRepository.findByPatientIdAndDateVisitStart(visitDTO.getPatientId(), visitDTO.getDateVisitStart());
        visit1.map(VisitService::exist);

        Optional <Patient> patient = patientRepository.findById(visitDTO.getPatientId());
        if(!patient.isPresent())throw new EntityNotFoundException(Patient.class, "Id", visitDTO.getPatientId()+"");


        Visit visit = visitMapper.toVisit(visitDTO);
        visit.setPatient(patient.get());
        this.visitRepository.save(visit);
        return visit;
    }


    public List<VisitDTO> getVisitByDateStart(LocalDate DateVisitStart) {
        // Converting 'dd-MM-yyyy' String format to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<Visit> visits = this.visitRepository.findByDateVisitStart(LocalDate.parse(formatter.format(LocalDate.now()), formatter));
        log.info("Just Checking in a patient(VisitService)...");

        if(visits.size() < 1){
            throw new EntityNotFoundException(Visit.class,"Date", LocalDate.parse(formatter.format(LocalDate.now()), formatter).toString());
        }
        System.out.println("We got here....");

        //List<PatientDTO> patientDTOs = new ArrayList<>();
        List<VisitDTO> visitDTOs = new ArrayList<>();

        visits.forEach(visit -> {
            Patient patient = patientRepository.getOne(visit.getPatient().getId());
            Person person = personRepository.getOne(patient.getPersonId());

            final VisitDTO visitDTO = visitMapper.toVisitDTO(visit, person);

            visitDTOs.add(visitDTO);
        });
        return visitDTOs;

    }

    /*public VisitDTO getSingleVisit(Long id){

        Optional<Visit> visit = visitRepository.findById(id);

        if (!visit.isPresent())
            throw new RecordNotFoundException();

        Visit visit1 = visit.get();

        VisitDTO visitDTO = new VisitDTO();

        visitDTO.setPatientId(visit1.getPatientId());
        visitDTO.setDateVisitStart(visit1.getDateVisitStart());
        visitDTO



    }*/
}
