package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.*;
import org.lamisplus.modules.base.domain.entities.*;
import org.lamisplus.modules.base.domain.mapper.EncounterMapper;
import org.lamisplus.modules.base.domain.mapper.FormMapper;
import org.lamisplus.modules.base.domain.mapper.PatientMapper;
import org.lamisplus.modules.base.domain.mapper.PersonRelativeMapper;
import org.lamisplus.modules.base.repository.*;
;
import org.lamisplus.modules.base.controller.RecordNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class PatientService {

    // Declaring variable used in the PatientService
    private static final String ENTITY_NAME = "patient";
    private final EncounterRepository encounterRepository;
    private final PatientRepository patientRepository;
    private final PersonRepository personRepository;
    private final PersonContactRepository personContactRepository;
    private final PersonRelativeRepository personRelativeRepository;
    private final VisitRepository visitRepository;
    private final PatientMapper patientMapper;
    private final PersonRelativeMapper personRelativeMapper;
    private final EncounterMapper encounterMapper;
    private final FormRepository formRepository;
    private final ProgramRepository programRepository;
    private final FormMapper formMapper;


    //Constructor initialization of Local variable taken care of by lombok


    private static Object exist(Class o, String Param1, String Param2) {
        throw new RecordExistException(o, Param1, Param2);
    }

    private static Object notExit(Class o, String Param1, String Param2) {
        throw new EntityNotFoundException(o, Param1, Param2);
    }

    /**
     * Saving a patient
     */
    public Person save(PatientDTO patientDTO) {
        //Creating a patient object
        Optional<Patient> patient1 = this.patientRepository.findByHospitalNumber(patientDTO.getHospitalNumber());
        if(patient1.isPresent()) exist(Patient.class, "Hospital Number", patientDTO.getHospitalNumber());

        final Person person = patientMapper.toPerson(patientDTO);
        final Person createdPerson = this.personRepository.save(person);

        final PersonContact personContact = patientMapper.toPersonContact(patientDTO);
        personContact.setPersonByPersonId(createdPerson);
        personContact.setPersonId(createdPerson.getId());

        log.info("SAVING person ... " + this.personContactRepository.save(personContact));

        if (patientDTO.getPersonRelativeDTOs()!= null || patientDTO.getPersonRelativeDTOs().size() > 0) {
            final List<PersonRelative> personRelatives = new ArrayList<>();
            patientDTO.getPersonRelativeDTOs().forEach(personRelativeDTO -> {
                final PersonRelative personRelative = personRelativeMapper.toPersonRelative(personRelativeDTO);
                personContact.setPersonByPersonId(createdPerson);
                personRelatives.add(personRelative);
            });
            this.personRelativeRepository.saveAll(personRelatives);
            log.info("SAVING person relatives ... "  + personRelatives);
        }
        //creating a patient object
        final Patient patient = patientMapper.toPatient(patientDTO);
        patient.setPersonByPersonId(createdPerson);
        patient.setPersonId(createdPerson.getId());
        //this.patientRepository.save(patient);
        log.info("SAVING patient relatives ... " + this.patientRepository.save(patient));

        return person;
    }

    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOs = new ArrayList<>();
        List<PersonRelativesDTO> personRelativeDTOs = new ArrayList<>();

        patients.forEach(patient -> {
            log.info("PATIENT  IS ..." + patient);
            Person person = patient.getPersonByPersonId();
            log.info("PERSON  IS ..." + person);
            PersonContact personContact = personContactRepository.findByPersonId(person.getId()).get();
            log.info("PERSON CONTACT  IS ..." + personContact);
            Optional<Visit> visitOptional = visitRepository.findTopByPatientIdAndDateVisitEndIsNullOrderByDateVisitStartDesc(patient.getId());
            PatientDTO patientDTO = null;
            if(visitOptional.isPresent()) {
                patientDTO = patientMapper.toPatientDTO(person, visitOptional.get(), personContact, patient);
            } else {
                patientDTO = patientMapper.toPatientDTO(person, personContact, patient);
            }

            List<PersonRelative> personRelatives = personRelativeRepository.findByPersonId(person.getId());
            if (personRelatives.size() > 0) {
                personRelatives.forEach(personRelative -> {
                    PersonRelativesDTO personRelativesDTO = personRelativeMapper.toPersonRelativeDTO(personRelative);
                    personRelativeDTOs.add(personRelativesDTO);
                });
                patientDTO.setPersonRelativeDTOs(personRelativeDTOs);
            }
            patientDTOs.add(patientDTO);
        });
        return patientDTOs;
    }

    public PatientDTO getPatientByHospitalNumber(String hospitalNumber) {
        Optional<Patient> patient = this.patientRepository.findByHospitalNumber(hospitalNumber);

        Person person = personRepository.getOne(patient.get().getPersonId());
        PersonContact personContact = personContactRepository.findByPersonId(person.getId()).get();
        Optional<Visit> visitOptional = visitRepository.findTopByPatientIdAndDateVisitEndIsNullOrderByDateVisitStartDesc(patient.get().getId());
        PatientDTO patientDTO = null;
        if(visitOptional.isPresent()) {
            patientDTO = patientMapper.toPatientDTO(person, visitOptional.get(), personContact, patient.get());
        } else {
            patientDTO = patientMapper.toPatientDTO(person, personContact, patient.get());
        }
        List<PersonRelativesDTO> personRelativeDTOs = new ArrayList<>();
        List<PersonRelative> personRelatives = personRelativeRepository.findByPersonId(person.getId());
        if (personRelatives != null || personRelatives.size() > 0) {
            personRelatives.forEach(personRelative -> {
                PersonRelativesDTO personRelativesDTO = personRelativeMapper.toPersonRelativeDTO(personRelative);
                personRelativeDTOs.add(personRelativesDTO);
            });
            patientDTO.setPersonRelativeDTOs(personRelativeDTOs);
        }
        return patientDTO;
    }



    public Person update(String hospitalNumber, PatientDTO patientDTO) {
        //Creating a patient object
        Optional<Patient> patient1 = this.patientRepository.findByHospitalNumber(hospitalNumber);
        if (!patient1.isPresent()) notExit(Patient.class, "Hospital Number", patientDTO.getHospitalNumber());

        final Person person = patientMapper.toPerson(patientDTO);
        final Person updatedPerson = this.personRepository.save(person);
        log.info("Updating person ... " + updatedPerson);

        final PersonContact personContact = patientMapper.toPersonContact(patientDTO);
        personContact.setPersonByPersonId(updatedPerson);
        //personContact.setPersonId(updatedPerson.getId());
        final PersonContact updatedContact = this.personContactRepository.save(personContact);

        log.info("Updating person Contact ... " + updatedContact);

        final List<PersonRelative> personRelatives = new ArrayList<>();
        patientDTO.getPersonRelativeDTOs().forEach(personRelativeDTO -> {
            final PersonRelative personRelative = personRelativeMapper.toPersonRelative(personRelativeDTO);
            personContact.setPersonByPersonId(updatedPerson);
            personRelatives.add(personRelative);
        });
        final List<PersonRelative> updatedRelative = this.personRelativeRepository.saveAll(personRelatives);
        log.info("Updating person relatives ... " + updatedRelative);

        //creating a patient object
        final Patient patient = patientMapper.toPatient(patientDTO);
        patient.setPersonByPersonId(updatedPerson);
        patient.setPersonId(updatedPerson.getId());
        final Patient updatedPatient = this.patientRepository.save(patient);
        log.info("Updating patient... " + updatedPatient);

        return person;
    }


    public List<EncounterDTO> getEncountersByPatientIdAndDateEncounter(Long patientId, String programCode, String formCode, Optional<String> dateBegin, Optional<String> dateEnd) {
        List<Encounter> encounters = encounterRepository.findAll(new Specification<Encounter>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(dateBegin.isPresent()){
                    LocalDate localDate = LocalDate.parse(dateBegin.get(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("dateEncounter").as(LocalDate.class), localDate)));
                }

                if(dateEnd.isPresent()){
                    LocalDate localDate = LocalDate.parse(dateEnd.get(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("dateEncounter").as(LocalDate.class), localDate)));
                }
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("patientId"), patientId)));
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("programCode"), programCode)));
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("formCode"), formCode)));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("dateEncounter")));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });

        return encounterList(encounters);
    }





    //Getting a single encounter for a patient
    public EncounterDTO getByPatientIdProgramCodeformCodelocalDate(Long PatientId, String programCode, String formCode, LocalDate localDate) {
        Optional<Encounter> Encounter = this.encounterRepository.findByPatientIdAndProgramCodeAndFormCodeAndDateEncounter(PatientId, programCode, formCode, localDate);

        //Handle error
        if (!Encounter.isPresent())
            throw new EntityNotFoundException(Encounter.class, "Patient Id ",PatientId.toString()+", Program Name = "+programCode+", Form Name ="+formCode+", Date ="+localDate);

        Encounter encounter = Encounter.get();
        Patient patient = this.patientRepository.findById(encounter.getPatientId()).get();

        Person person = this.personRepository.findById(patient.getPersonId()).get();

        final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, encounter);

        log.info("GETTING encounter by Program, Form,  Date... " + encounterDTO);

        return encounterDTO;

    }

    //EDITED - 19/03/2020
    public List<EncounterDTO> getAllEncountersByPatientId(Long patientId) {
        List<Encounter> tempEncounter = encounterRepository.findBypatientId(patientId);

        return encounterList(tempEncounter);
    }



    public EncounterDTO getEncountersByPatientIdAndVisitId(Long PatientId, String programCode, String formCode, Long VisitId) {
        Optional<Visit> visit = this.visitRepository.findById(VisitId);
        if(!visit.isPresent()) throw new EntityNotFoundException(Visit.class, "Visit Id", VisitId + "");

        Optional<Encounter> encounterOptional = this.encounterRepository.findFirstByPatientIdAndProgramCodeAndFormCodeAndVisitIdOrderByDateEncounterDesc(PatientId, programCode, formCode, VisitId);

        if (!encounterOptional.isPresent()) throw new EntityNotFoundException(Encounter.class, "Patient Id ",PatientId.toString()+", " +
                "Program Name = "+programCode+", Form Code ="+formCode +", Visit Id ="+VisitId);

        Encounter encounter = encounterOptional.get();
        Patient patient = this.patientRepository.findById(encounter.getPatientId()).get();

        Person person = this.personRepository.findById(patient.getPersonId()).get();

        final EncounterDTO encounterDTO1 = encounterMapper.toEncounterDTO(person, patient, encounter);

        log.info("GETTING encounter Latest 12... " + encounterDTO1);
        //issues may come up with wrong form name
        return encounterDTO1;
    }



    public List<EncounterDTO> getEncountersByPatientId(Long pid, String programCode, String formCode, String sortField, String sortOrder, Integer limit) {
        Pageable pageableSorter = createPageRequest(sortField, sortOrder, limit);
        //List<Object> formDataList = new ArrayList<>();
        List<EncounterDTO> encounterDTOS = new ArrayList<>();

        List<Encounter> encountersList = encounterRepository.findAllByPatientIdAndProgramCodeAndFormCode(pid,programCode, formCode,pageableSorter);
        encountersList.forEach(encounter -> {
            Patient patient = this.patientRepository.findById(encounter.getPatientId()).get();
            Person person = this.personRepository.findById(patient.getPersonId()).get();

            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, encounter);
            /*formDataList.add(encounterDTO.getFormData());
            encounterFormDataListDTO = encounterMapper.toEncounterFormListDTO(encounterDTO);*/
            encounterDTOS.add(encounterDTO);
            log.info("GETTING encounter in List by getDateByRange... " + encounterDTO);


        });

        return encounterDTOS;
    }

    public List<EncounterDTO> getFormsByPatientId(Long PatientId, String formCode) {
        List<Encounter> encounters = this.encounterRepository.findAllByPatientIdAndFormCode(PatientId, formCode);

        return encounterList(encounters);
    }

    public List<Object> testing(Long PatientId, String formCode) {
        List<Encounter> encounters = this.encounterRepository.findAllByPatientIdAndFormCode(PatientId, formCode);
        List<Object> formData = new ArrayList<>();
        encounters.forEach(encounter -> {
            formData.add(encounter.getFormData());
        });
        return formData;
    }


    private Pageable createPageRequest(String sortField, String sortOrder, Integer limit) {
        if(sortField == null){
            sortField = "dateEncounter";
        }
        if(limit == null){
            limit = 4;
        }
        if(sortOrder != null && sortOrder.equalsIgnoreCase("Asc")) {
            return PageRequest.of(0, limit, Sort.by(sortField).ascending());

        } else if(sortOrder != null && sortOrder.equalsIgnoreCase("Desc")) {
            return PageRequest.of(0, limit, Sort.by(sortField).descending());

        }else
            return PageRequest.of(0, limit, Sort.by(sortField).ascending());

    }

    List<EncounterDTO> encounterList(List<Encounter> encounters){
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

    public Boolean delete(Long id, Patient patient) {
        return true;
    }


    //TOdo add a method to get patient Relative - to avoid duplicate codes

}
