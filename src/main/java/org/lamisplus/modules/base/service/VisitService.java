package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.VisitDTO;
import org.lamisplus.modules.base.domain.entity.Patient;
import org.lamisplus.modules.base.domain.entity.Person;
import org.lamisplus.modules.base.domain.entity.Visit;
import org.lamisplus.modules.base.domain.mapper.VisitMapper;
import org.lamisplus.modules.base.repository.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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




    public List<VisitDTO> getAllVisits() {
        List<Visit> visits = visitRepository.findAll();
        return visitList(visits);

    }

    //Saving a Visit/ Checking in a patient
    public Visit save(VisitDTO visitDTO) {
        Optional<Visit> visitOptional = this.visitRepository.findByPatientIdAndDateVisitStart(visitDTO.getPatientId(), visitDTO.getDateVisitStart());
        if(visitOptional.isPresent())throw new RecordExistException(Visit.class, "Id", visitOptional.get().getId()+"");

        Optional <Patient> patient = patientRepository.findById(visitDTO.getPatientId());
        if(!patient.isPresent())throw new EntityNotFoundException(Patient.class, "Id", visitDTO.getPatientId()+"");

        Visit visit = visitMapper.toVisit(visitDTO);
        //visit.setPatientByPatientId(patient.get());
        this.visitRepository.save(visit);
        return visit;
    }


    public VisitDTO getVisit(Long id) {
        Optional<Visit> visitOptional = this.visitRepository.findById(id);
        if (!visitOptional.isPresent()) throw new EntityNotFoundException(Visit.class, "Id", id + "");
        Optional<Person> personOptional = this.personRepository.findById(visitOptional.get().getPatient().getPersonId());
        VisitDTO visitDTO = visitMapper.toVisitDTO(visitOptional.get(), personOptional.get());
        return visitDTO;
    }

    public Visit update(Long id, Visit visit) {
        Optional<Visit> visitOptional = this.visitRepository.findById(id);
        if(!visitOptional.isPresent())throw new EntityNotFoundException(Visit.class, "Id", id +"");
        visit.setId(id);
        return visitRepository.save(visit);
    }

    public Boolean delete(Long id, Visit visit) {
        return true;
    }

    public List<VisitDTO> visitList(List<Visit> visits){
        List<VisitDTO> visitDTOs = new ArrayList<>();
        visits.forEach(visit -> {
            Patient patient = visit.getPatient();
            Person person = patient.getPersonByPersonId();

            final VisitDTO visitDTO = visitMapper.toVisitDTO(visit, person);

            visitDTOs.add(visitDTO);
        });
        return visitDTOs;

    }










}
