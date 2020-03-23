package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entities.*;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.dto.EncounterDTO;
import org.lamisplus.modules.base.domain.dto.LabTestDTO;

import org.lamisplus.modules.base.domain.mapper.EncounterMapper;
import org.lamisplus.modules.base.repository.*;

import org.lamisplus.modules.base.controller.RecordNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private static final String ENTITY_NAME = "encounter";
    private final PatientRepository patientRepository;
    private final PersonRepository personRepository;
    private final ServicesRepository servicesRepository;
    private final EncounterRepository encounterRepository;
    private final VisitRepository visitRepository;
    private final FormRepository formRepository;
    private final EncounterMapper encounterMapper;

    private static Object exist(Encounter o) {
        throw new EntityNotFoundException(Encounter.class, "Id", "id Already Exist");
    }

    private static Encounter notExit() {
        throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id is null");
    }

    public Encounter save(EncounterDTO encounterDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        LocalTime now = LocalTime.parse(formatter.format(LocalTime.now()), formatter);

        encounterDTO.setTimeCreated(now);

        /* Form form = formRepository.findByName(encounterDTO.getFormName());

        Service service = this.servicesRepository.findByServiceName(encounterDTO.getServiceName());

        //Checking is the patient has been checked in by visit id
        Visit visit = this.visitRepository.getOne(encounterDTO.getVisitId());
        */
        Encounter encounter = encounterMapper.toEncounter(encounterDTO);
        //encounter = this.encounterProcessor.preProcess(encounter);
        encounter = this.encounterRepository.save(encounter);
        //encounter = this.encounterProcessor.postProcess(encounter);
        return encounter;

    }

    //Getting a single encounter for a patient
    public EncounterDTO getByPatientIdServiceNameFormNamelocalDate(Long PatientId, String ServiceName, String FormName, LocalDate localDate) {

        Optional<Encounter> Encounter = this.encounterRepository.findByPatientIdAndServiceNameAndFormNameAndDateEncounter(PatientId, ServiceName, FormName, localDate);

        //Handle error
        if (!Encounter.isPresent())
            throw new EntityNotFoundException(Encounter.class, "Patient Id ",PatientId.toString()+", Service Name = "+ServiceName+", Form Name ="+FormName+", Date ="+localDate);

        Encounter encounter = Encounter.get();
        Patient patient = this.patientRepository.findById(encounter.getPatientId()).get();

        /*if (patient.getArchive() == 0)
            throw new RecordNotFoundException();
*/
        Person person = this.personRepository.findById(patient.getPersonId()).get();

        final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, encounter);

        log.info("GETTING encounter by Service, Form,  Date... " + encounterDTO);

        return encounterDTO;

    }

    //EDITED - 19/03/2020...
    public List<EncounterDTO> getAllEncounter() {
        List<EncounterDTO> encounterDTOS = new ArrayList();
        List <Encounter> allEncounter = encounterRepository.findAll();
        allEncounter.forEach(SingleEncounter -> {

            Patient patient = this.patientRepository.findById(SingleEncounter.getPatientId()).get();
            Person person = this.personRepository.findById(patient.getPersonId()).get();

            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, SingleEncounter);

            log.info("GETTING encounter in List 12... " + encounterDTO);

            encounterDTOS.add(encounterDTO);


        });

        return encounterDTOS;
    }

    //EDITED - 19/03/2020
    public List<EncounterDTO> getAllByPatientId(Long patientId) {
        List<EncounterDTO> encounterDTOS = new ArrayList();

        List<Encounter> tempEncounter = encounterRepository.findBypatientId(patientId);

        if (tempEncounter.size() < 1)
            throw new RecordNotFoundException();

        tempEncounter.forEach(OnePatientEncounter -> {
            Patient patient = this.patientRepository.findById(OnePatientEncounter.getPatientId()).get();

            Person person = this.personRepository.findById(patient.getPersonId()).get();

            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, OnePatientEncounter);
            log.info("GETTING encounter in List by Pid 123456... " + encounterDTO);

            encounterDTOS.add(encounterDTO);
        });

        return encounterDTOS;
    }

    public EncounterDTO getByEncounterId(Long id) {
        Optional<Encounter> patientEncounter = this.encounterRepository.findById(id);
        if (!patientEncounter.isPresent())
            throw new EntityNotFoundException(Encounter.class, "Patient Id ",id.toString());

        Encounter encounter = patientEncounter.get();

        Patient patient = this.patientRepository.findById(encounter.getPatientId()).get();
/*
        if (patient.getArchive() == 0)
            throw new RecordNotFoundException();
*/
        Person person = this.personRepository.findById(patient.getPersonId()).get();

        EncounterDTO encounterDTO1 =  encounterMapper.toEncounterDTO(person, patient, encounter);

        return encounterDTO1;
    }

    //UPDATING AN ENCOUNTER
    public Encounter updateEncounter(EncounterDTO encounterDTO) {
        log.info(encounterDTO.getEncounterId().toString());
        Optional<Encounter> encounter = this.encounterRepository.findById(encounterDTO.getEncounterId());
        encounter.orElseGet(EncounterService::notExit);

        final Encounter encounter1 = encounterMapper.toEncounter(encounterDTO);

        //UPDATING THE ENCOUNTER
        this.encounterRepository.save(encounter1);

        return encounter1;
    }

    //FOR SAVING ALL CONSULTATION FORM......
    public Encounter saveConsultation(EncounterDTO encounterDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(formatter.format(LocalTime.now()), formatter);

        encounterDTO.setTimeCreated(time);
        log.info("Getting Consultation from front end..." + encounterDTO);

        Optional<Encounter> encounter = this.encounterRepository.findByPatientIdAndServiceNameAndFormNameAndDateEncounter(encounterDTO.getPatientId(), encounterDTO.getFormName(),
                encounterDTO.getServiceName(), encounterDTO.getDateEncounter());

            if (encounter.isPresent()) {
                throw new RecordExistException(Encounter.class, "Patient Id ", encounterDTO.getPatientId() + ", " +
                        "Service Name = " + encounterDTO.getServiceName() + ", Form Name =" + encounterDTO.getFormName() + ", Date =" + encounterDTO.getDateEncounter());
            }

            Optional <Patient> patient1 = this.patientRepository.findById(encounterDTO.getPatientId());
            if(!patient1.isPresent()){
                throw new EntityNotFoundException(Patient.class,"Patient Id", patient1.get().getId().toString());
            }
            //Optional<Person> person = this.personRepository.findById(patient1.get().getId());
            Optional<Form> form = formRepository.findByName(encounterDTO.getFormName());
            if(!form.isPresent()){
                throw new EntityNotFoundException(Form.class,"Form Name", encounterDTO.getFormName());
            }

            Optional<Service> service = this.servicesRepository.findByServiceName(encounterDTO.getServiceName());
            if(!service.isPresent()){
                throw new EntityNotFoundException(Service.class,"Service Name", encounterDTO.getServiceName());
            }

            Optional<Visit> visit = this.visitRepository.findById(encounterDTO.getVisitId());
            if(!visit.isPresent()){
                throw new EntityNotFoundException(Visit.class,"Visit Id", encounterDTO.getVisitId().toString());
            }

            final Encounter encounter1 = encounterMapper.toEncounter(encounterDTO);

            Encounter encounter2 = this.encounterRepository.save(encounter1);
            log.info("SAVING CONSULTATION TO BACKEND 12345... " + this.encounterRepository.save(encounter2));

        return encounter2;

    }

    //Getting a single encounter for a patient
    //GETTING LATEST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION)
    public EncounterDTO getLatestEncounter(Long PatientId, String ServiceName, String FormName) {
        Optional<Encounter> patientEncounter = this.encounterRepository.findFirstByPatientIdAndServiceNameAndFormNameOrderByDateEncounterDesc(PatientId, ServiceName, FormName);
        //patientEncounter.map(EncounterService::exist);
        if (!patientEncounter.isPresent())
            throw new EntityNotFoundException(Encounter.class, "Patient Id ",PatientId.toString()+", Service Name = "+ServiceName+", Form Name ="+FormName);

        Encounter encounter = patientEncounter.get();
        Patient patient = this.patientRepository.findById(encounter.getPatientId()).get();

        //Creating a person object
        Person person = this.personRepository.findById(patient.getPersonId()).get();

        final EncounterDTO encounterDTO1 = encounterMapper.toEncounterDTO(person, patient, encounter);

        log.info("GETTING encounter Latest 12... " + encounterDTO1);
        //issues may come up with wrong form name
        return encounterDTO1;
    }

    //Get all latest encounter
    public List<EncounterDTO> getAllLatestEncounter(LocalDate DateEncounter, String serviceName, String formName) {
        List<EncounterDTO> encounterDTOS = new ArrayList();

        List<Encounter> tempEncounter = encounterRepository.findAllByServiceNameAndFormNameAndDateEncounterOrderByDateEncounterDesc(serviceName, formName, DateEncounter);

        //Handling errors
        if (tempEncounter.size() < 1) {
            throw new EntityNotFoundException(Encounter.class, "Date ", String.valueOf(DateEncounter));
        }

        tempEncounter.forEach(OnePatientEncounter -> {
            Patient patient = this.patientRepository.findById(OnePatientEncounter.getPatientId()).get();
/*
            if (patient.getArchive() == 0)
                return;
*/
            Person person = this.personRepository.findById(patient.getPersonId()).get();
            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, OnePatientEncounter);

            log.info("GETTING encounter in List by Pid 12... " + encounterDTO);

            encounterDTOS.add(encounterDTO);
        });

        return encounterDTOS;
    }

    //Get Last Encounter
    public EncounterDTO getLastEncounter(Long patientId, String serviceName, String formName) {
        Optional <Encounter> encounter = encounterRepository.findTopByServiceNameAndFormNameAndPatientIdOrderByDateEncounterDesc(serviceName, formName, patientId);

       return this.findEncounter(encounter, patientId, serviceName, formName);
    }


    //Get first Encounter
    public EncounterDTO getFirstEncounter(Long patientId, String serviceName, String formName) {
        Optional <Encounter> encounter = encounterRepository.findTopByServiceNameAndFormNameAndPatientIdOrderByDateEncounterAsc(serviceName, formName, patientId);

        return this.findEncounter(encounter, patientId, serviceName, formName);

        }


    public List<EncounterDTO> getEncounterByDate(String serviceName, String formName, LocalDate dateStart, LocalDate dateEnd) {
        List<EncounterDTO> encounterDTOS = new ArrayList();

        List <Encounter> encounterList = encounterRepository.findAllByServiceNameAndFormNameAndDateEncounterIsBetweenQuery(serviceName, formName, dateStart, dateEnd);

        encounterList.forEach(singleEncounter -> {
            Patient patient = this.patientRepository.findById(singleEncounter.getPatientId()).get();
            Person person = this.personRepository.findById(patient.getPersonId()).get();

            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, singleEncounter);
            log.info("GETTING encounter in List by getDateByRange... " + encounterDTO);

            encounterDTOS.add(encounterDTO);

        });

        return encounterDTOS;

    }

        //Custom in built method(Helper method)
    private EncounterDTO findEncounter(Optional <Encounter> encounter, Long patientId, String serviceName, String formName){

        //Handle error
        if (!encounter.isPresent())
            throw new EntityNotFoundException(Encounter.class, "Patient Id ",patientId.toString()+", Service Name ="+serviceName+", Form Name ="+formName);
        Patient patient = this.patientRepository.findById(patientId).get();
        Person person = this.personRepository.findById(patient.getPersonId()).get();

        final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, encounter.get());

        return encounterDTO;

    }

    public List<EncounterDTO> getSortedEncounter(Long pid, String serviceName, String formName, String sortField, String sortOrder, Integer limit) {
        Pageable pageableSorter = PageRequest.of(0, 4, Sort.by("dateEncounter").descending());

        if(sortField == null || sortOrder == null || sortOrder == null || limit == null) {
            pageableSorter = PageRequest.of(0, 4, Sort.by("dateEncounter").descending());
        } else if(sortOrder != null || !sortOrder.equals("") || sortOrder.equalsIgnoreCase("Asc")) {
            pageableSorter = PageRequest.of(0, limit, Sort.by(sortField).ascending());
        } else if(!sortOrder.equals("") || !sortOrder.equals("") || sortOrder != null || sortOrder.equalsIgnoreCase("Desc")) {
            pageableSorter = PageRequest.of(0, limit, Sort.by(sortField).descending());
        }
        List<Encounter> encountersList = encounterRepository.findAllByPatientIdAndServiceNameAndFormName(pid,serviceName, formName,pageableSorter);

        List<EncounterDTO> encounterDTOS = new ArrayList();
        encountersList.forEach(OneEncounter -> {
            Patient patient = this.patientRepository.findById(OneEncounter.getPatientId()).get();
            Person person = this.personRepository.findById(patient.getPersonId()).get();

            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, OneEncounter);
            log.info("GETTING encounter in List by getDateByRange... " + encounterDTO);

            encounterDTOS.add(encounterDTO);

        });

        return encounterDTOS;
    }

    public EncounterDTO getEncounterByVisitId(Long PatientId, String ServiceName, String FormName, Long VisitId) {
        Optional<Visit> visit = this.visitRepository.findById(VisitId);
        if(!visit.isPresent()) throw new EntityNotFoundException(Visit.class, "Visit Id", VisitId + "");

        Optional<Encounter> encounterOptional = this.encounterRepository.findFirstByPatientIdAndServiceNameAndFormNameAndVisitIdOrderByDateEncounterDesc(PatientId, ServiceName, FormName, VisitId);

        if (!encounterOptional.isPresent()) throw new EntityNotFoundException(Encounter.class, "Patient Id ",PatientId.toString()+", " +
                "Service Name = "+ServiceName+", Form Name ="+FormName +", Visit Id ="+VisitId);

        Encounter encounter = encounterOptional.get();
        Patient patient = this.patientRepository.findById(encounter.getPatientId()).get();

        //Creating a person object
        Person person = this.personRepository.findById(patient.getPersonId()).get();

        final EncounterDTO encounterDTO1 = encounterMapper.toEncounterDTO(person, patient, encounter);

        log.info("GETTING encounter Latest 12... " + encounterDTO1);
        //issues may come up with wrong form name
        return encounterDTO1;
    }

    public List<EncounterDTO> getSortedEncounter(String serviceName, String formName, Optional<String> dateStart, Optional<String> dateEnd) {
        List<Encounter> encounters = encounterRepository.findAll(new Specification<Encounter>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(dateStart.isPresent()){
                    LocalDate localDate = LocalDate.parse(dateStart.get(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("dateEncounter").as(LocalDate.class), localDate)));
                }

                if(dateEnd.isPresent()){
                    LocalDate localDate = LocalDate.parse(dateEnd.get(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("dateEncounter").as(LocalDate.class), localDate)));
                }
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("serviceName"), serviceName)));
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("formName"), formName)));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("dateEncounter")));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
       // List <Encounter> encounterList = encounterRepository.findAllByServiceNameAndFormNameAndDateEncounterIsBetweenQuery(serviceName, formName, dateStart, dateEnd);
        List<EncounterDTO> encounterDTOS = new ArrayList<>();
        encounters.forEach(singleEncounter -> {
            Patient patient = this.patientRepository.findById(singleEncounter.getPatientId()).get();
            Person person = this.personRepository.findById(patient.getPersonId()).get();

            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, singleEncounter);
            log.info("GETTING encounter in List by getDateByRange... " + encounterDTO);

            encounterDTOS.add(encounterDTO);

        });

        return encounterDTOS;

    }
}