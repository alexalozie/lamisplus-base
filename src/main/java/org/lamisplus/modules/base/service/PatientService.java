package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.*;
import org.lamisplus.modules.base.domain.entity.*;
import org.lamisplus.modules.base.domain.mapper.*;
import org.lamisplus.modules.base.repository.*;
;
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
    private final VisitMapper visitMapper;

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
            //log.info("PERSON  IS ..." + person);
            PersonContact personContact = personContactRepository.findByPersonId(person.getId()).get();
            //log.info("PERSON CONTACT  IS ..." + personContact);
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

        Person person = patient.get().getPersonByPersonId();
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



    public Person update(Long id, PatientDTO patientDTO) {
        //Creating a patient object
        Optional<Patient> patient1 = this.patientRepository.findById(id);
        if (!patient1.isPresent()) notExit(Patient.class, "Id", id+"");

        final Person person = patientMapper.toPerson(patientDTO);
        person.setId(patient1.get().getId());
        final Person updatedPerson = this.personRepository.save(person);
        log.info("Updating person ... " + updatedPerson);

        final PersonContact personContact = patientMapper.toPersonContact(patientDTO);
        Optional<PersonContact> personContact1 = this.personContactRepository.findByPersonId(updatedPerson.getId());
        personContact.setId(personContact1.get().getId());
        //personContact.setPersonByPersonId(updatedPerson);
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


    public List getEncountersByPatientIdAndDateEncounter(Long patientId, String formCode, Optional<String> dateStart, Optional<String> dateEnd) {
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
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("patientId"), patientId)));
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("formCode"), formCode)));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("dateEncounter")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });

        return listEncounterProcessor(encounters);
    }

    public List getAllEncountersByPatientId(Long patientId) {
        List<Encounter> encounters = encounterRepository.findByPatientId(patientId);

        return listEncounterProcessor(encounters);
    }

    public EncounterDTO getEncountersByPatientIdAndVisitId(Long PatientId, String programCode, String formCode, Long VisitId) {
        Optional<Visit> visit = this.visitRepository.findById(VisitId);
        if(!visit.isPresent()) throw new EntityNotFoundException(Visit.class, "Visit Id", VisitId + "");

        Optional<Encounter> encounterOptional = this.encounterRepository.findFirstByPatientIdAndProgramCodeAndFormCodeAndVisitIdOrderByDateEncounterDesc(PatientId, programCode, formCode, VisitId);

        if (!encounterOptional.isPresent()) throw new EntityNotFoundException(Encounter.class, "Patient Id ",PatientId.toString()+", " +
                "Program Name = "+programCode+", Form Code ="+formCode +", Visit Id ="+VisitId);

        Encounter encounter = encounterOptional.get();
        Patient patient = encounter.getPatientByPatientId();

        Person person = patient.getPersonByPersonId();

        Program program = encounter.getProgramByProgramCode();

        //List<FormData> formData = encounter.getFormData();

        final EncounterDTO encounterDTO1 = encounterMapper.toEncounterDTO(person, patient, encounter, program);

        log.info("GETTING encounter Latest 12... " + encounterDTO1);
        //issues may come up with wrong form name
        return encounterDTO1;
    }



    public List getEncountersByPatientId(Long patientId, String formCode, String sortField, String sortOrder, Integer limit) {
        Pageable pageableSorter = createPageRequest(sortField, sortOrder, limit);
        List<Encounter> encountersList = encounterRepository.findAllByPatientIdAndFormCode(patientId,formCode,pageableSorter);
        return listEncounterProcessor(encountersList);
    }

    public List getEncountersByPatientIdAndProgramCodeExclusionList(Long patientId, List<String> programCodeExclusionList) {
        List<Encounter> encounters = encounterRepository.findAll(new Specification<Encounter>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("patientId"), patientId)));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("dateEncounter")));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
        List<EncounterDTO> encounterDTOS = new ArrayList<>();
        if(programCodeExclusionList.size() > 0 && !programCodeExclusionList.isEmpty())
            programCodeExclusionList.forEach(programCode ->{
                log.info("Exclusion list is" + programCode);
                encounters.forEach(singleEncounter -> {
                    if(singleEncounter.getProgramCode().equals(programCode))return;
                    Patient patient = singleEncounter.getPatientByPatientId();
                    Person person = patient.getPersonByPersonId();
                    Program program = singleEncounter.getProgramByProgramCode();

                    final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, singleEncounter, program);
                    encounterDTOS.add(encounterDTO);
                });
            });
        return encounterDTOS;

        }

    public List<EncounterDTO> testing(Long PatientId, String formCode) {
        List<Encounter> encounters = this.encounterRepository.findAllByPatientIdAndFormCode(PatientId, formCode);
        return listEncounterProcessor(encounters);
    }

    public Boolean delete(Long id, Patient patient) {
        return true;
    }

    public List<VisitDTO> getVisitByPatientIdAndVisitDate(Optional <Long> patientId, Optional<String> dateStart, Optional<String> dateEnd) {
        List<Visit> visitList = visitRepository.findAll(new Specification<Visit>() {
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
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("dateVisitStart")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });

        List<VisitDTO> visitDTOList = new ArrayList<>();
        visitList.forEach(visit -> {
            Patient patient = visit.getPatient();
            Person person = patient.getPersonByPersonId();
            final VisitDTO visitDTO = visitMapper.toVisitDTO(visit, person);

            visitDTOList.add(visitDTO);
        });

        return visitDTOList;
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

    private List listEncounterProcessor(List<Encounter> encounters){
        List<Object> formDataList = new ArrayList<>();
        encounters.forEach(encounter -> {
            encounter.getFormData().forEach(formData1 -> {
                formDataList.add(formData1.getData());
                log.info("GETTING encounter in List by encounter.formData1.getData()... " + formData1.getData());

            });
        });
        return formDataList;
    }

    //TOdo add a method to get patient Relative - to avoid duplicate codes

}
