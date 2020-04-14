package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entity.*;
import org.lamisplus.modules.base.domain.dto.EncounterDTO;

import org.lamisplus.modules.base.domain.mapper.EncounterMapper;
import org.lamisplus.modules.base.repository.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EncounterService {
    private final PatientRepository patientRepository;
    private final ProgramRepository programRepository;
    private final EncounterRepository encounterRepository;
    private final VisitRepository visitRepository;
    private final FormRepository formRepository;
    private final EncounterMapper encounterMapper;
    private final FormDataRepository formDataRepository;




    public List<EncounterDTO> getAllEncounters() {
        List<EncounterDTO> encounterDTOS = new ArrayList();
        List <Encounter> allEncounter = encounterRepository.findAll();
        allEncounter.forEach(singleEncounter -> {
            Patient patient = singleEncounter.getPatientByPatientId();
            Person person = patient.getPersonByPersonId();
            Form form = singleEncounter.getEncounterByFormCode();
            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, singleEncounter, form);

            log.info("GETTING encounter in List 12... " + encounterDTO);

            encounterDTOS.add(encounterDTO);
        });

        return encounterDTOS;
    }

    public EncounterDTO getEncounter(Long id) {
        Optional<Encounter> encounterOptional = encounterRepository.findById(id);
        Patient patient = encounterOptional.get().getPatientByPatientId();
        Person person = patient.getPersonByPersonId();
        Form form = encounterOptional.get().getEncounterByFormCode();

        final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, encounterOptional.get(), form);

        log.info("GETTING encounter in List 12... " + encounterDTO);

        return encounterDTO;
    }


    //UPDATING AN ENCOUNTER
    public Encounter update(Long id, EncounterDTO encounterDTO) {
        Optional<Encounter> encounterOptional = this.encounterRepository.findById(id);
        if(!encounterOptional.isPresent()) throw new EntityNotFoundException(Encounter.class, "Id",id+"" );
        Encounter encounter = encounterMapper.toEncounter(encounterDTO);
        encounter.setId(id);
        this.encounterRepository.save(encounter);

        return encounter;
    }

    //FOR SAVING ALL CONSULTATION FORM......
    public Encounter save(EncounterDTO encounterDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(formatter.format(LocalTime.now()), formatter);

        encounterDTO.setTimeCreated(time);
        log.info("Getting Consultation from front end..." + encounterDTO);

        Optional<Encounter> encounter = this.encounterRepository.findByPatientIdAndProgramCodeAndFormCodeAndDateEncounter(encounterDTO.getPatientId(), encounterDTO.getFormCode(),
                encounterDTO.getProgramCode(), encounterDTO.getDateEncounter());

        if (encounter.isPresent()) {
            throw new RecordExistException(Encounter.class, "Patient Id ", encounterDTO.getPatientId() + ", " +
                    "Program Code = " + encounterDTO.getProgramCode() + ", Form Code =" + encounterDTO.getFormCode() + ", Date =" + encounterDTO.getDateEncounter());
        }

        Optional <Patient> patient1 = this.patientRepository.findById(encounterDTO.getPatientId());
        if(!patient1.isPresent()){
            throw new EntityNotFoundException(Patient.class,"Patient Id", patient1.get().getId().toString());
        }

        Optional<Form> form = formRepository.findByCode(encounterDTO.getFormCode());
        if(!form.isPresent()){
            throw new EntityNotFoundException(Form.class,"Form Name", encounterDTO.getFormCode());
        }

        Optional<Program> program = this.programRepository.findByCode(encounterDTO.getProgramCode());
        if(!program.isPresent()){
            throw new EntityNotFoundException(Program.class,"Program Name", encounterDTO.getProgramCode());
        }

        Optional<Visit> visit = this.visitRepository.findById(encounterDTO.getVisitId());
        if(!visit.isPresent()){
            throw new EntityNotFoundException(Visit.class,"Visit Id", encounterDTO.getVisitId().toString());
        }


        final Encounter encounter1 = encounterMapper.toEncounter(encounterDTO);


        Encounter encounter2 = this.encounterRepository.save(encounter1);
        log.info("SAVING CONSULTATION TO BACKEND 12345... " + encounter2);

        if(encounterDTO.getData().size() >0){
            encounterDTO.getData().forEach(formDataList->{
                FormData formData = new FormData();
                formData.setEncounterId(encounter2.getId());
                formData.setData(formDataList);
                this.formDataRepository.save(formData);
            });

        }

        return encounter2;

    }

    public Boolean delete(Long id, Encounter encounter) {
        return true;
    }

    public List<EncounterDTO> getEncounterByFormCodeAndDate(String FormCode, Optional<String> dateStart, Optional<String> dateEnd) {

        List<Encounter> encounters = encounterRepository.findAll(new Specification<Encounter>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(dateStart.isPresent() && !dateStart.get().equals("{dateStart}")){
                    LocalDate localDate = LocalDate.parse(dateStart.get(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("dateEncounter").as(LocalDate.class), localDate)));
                }

                if(dateEnd.isPresent() && !dateEnd.get().equals("{dateEnd}")){
                    LocalDate localDate = LocalDate.parse(dateEnd.get(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("dateEncounter").as(LocalDate.class), localDate)));
                }
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("formCode"), FormCode)));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("dateEncounter")));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });

        // List <Encounter> encounterList = encounterRepository.findAllByprogramCodeAndFormCodeAndDateEncounterIsBetweenQuery(programCode, FormCode, dateStart, dateEnd);
        List<EncounterDTO> encounterDTOS = new ArrayList<>();
        encounters.forEach(singleEncounter -> {
            Patient patient = singleEncounter.getPatientByPatientId();
            Person person = patient.getPersonByPersonId();
            Form form = singleEncounter.getEncounterByFormCode();

            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, singleEncounter, form);
            log.info("GETTING encounter in List by getDateByRange... " + encounterDTO);

            encounterDTOS.add(encounterDTO);

        });
        return encounterDTOS;
    }

    public List getFormDataByEncounterId(Long encounterId) {
        Encounter encounter = encounterRepository.getOne(encounterId);
        List<FormData> formDataList = encounter.getFormData();
        return formDataList;
    }

    /*public List getFormDataByEncounterFormCode(Long id) {
        Optional<Encounter> encounterOptional = encounterRepository.findById(id);
        List<FormData> formDataList = encounterOptional.get().getFormData();
        List data = new ArrayList();

        encounters.forEach(encounter -> {
            List<FormData> formDataList = encounter.getFormData();
            formDataList.forEach(formData -> {
                data.add(formData.getData());
            });

        });
        return data;
    }*/
}
