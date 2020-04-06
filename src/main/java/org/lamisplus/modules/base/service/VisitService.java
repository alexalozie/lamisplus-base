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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    public List<VisitDTO> getVisitByPatientIdAndVisitDate(Optional <Long> patientId, Optional<String> dateStart, Optional<String> dateEnd) {

        List<Visit> visits = visitRepository.findAll(new Specification<Visit>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (patientId.isPresent()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("patientId").as(Long.class), patientId.get())));
                }
                if (dateStart.isPresent()) {
                    LocalDate localDate = LocalDate.parse(dateStart.get(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("dateVisitStart").as(LocalDate.class), localDate)));
                }
                if (dateEnd.isPresent()) {
                    LocalDate localDate = LocalDate.parse(dateEnd.get(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("dateVisitStart").as(LocalDate.class), localDate)));
                }
                    /*String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("dateVisitStart").as(LocalDate.class), localDate)));*/

                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("dateVisitStart")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });

        return visitList(visits);
    }


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
            Patient patient = patientRepository.getOne(visit.getPatient().getId());
            Person person = personRepository.getOne(patient.getPersonId());

            final VisitDTO visitDTO = visitMapper.toVisitDTO(visit, person);

            visitDTOs.add(visitDTO);
        });
        return visitDTOs;

    }










}
