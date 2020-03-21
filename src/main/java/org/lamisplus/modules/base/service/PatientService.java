package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entities.Patient;
import org.lamisplus.modules.base.domain.entities.Person;
import org.lamisplus.modules.base.domain.entities.PersonContact;
import org.lamisplus.modules.base.domain.entities.PersonRelative;
import org.lamisplus.modules.base.domain.mapper.PatientMapper;
import org.lamisplus.modules.base.domain.mapper.PersonRelativeMapper;
import org.lamisplus.modules.base.repository.*;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.dto.PatientDTO;
import org.lamisplus.modules.base.domain.dto.PersonRelativesDTO;;
import org.lamisplus.modules.base.controller.RecordNotFoundException;
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
public class PatientService {

    // Declaring variable used in the PatientService
    private static final String ENTITY_NAME = "patient";
    private final PatientRepository patientRepository;
    private final PersonRepository personRepository;
    private final PersonContactRepository personContactRepository;
    private final PersonRelativeRepository personRelativeRepository;
    private final PatientMapper patientMapper;
    private final PersonRelativeMapper personRelativeMapper;

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

    public List<PatientDTO> getAllPatient() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOs = new ArrayList<>();
        List<PersonRelativesDTO> personRelativeDTOs = new ArrayList<>();

        patients.forEach(patient -> {
            Person person = personRepository.getOne(patient.getPersonId());
            log.info("PERSON  IS ..." + person);
            PersonContact personContact = personContactRepository.findByPersonId(person.getId()).get();
            PatientDTO patientDTO = patientMapper.toPatientDTO(person, personContact, patient);

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
        //Handle error
        if (!patient.isPresent()) notExit(Patient.class, "Hospital Number", hospitalNumber + "");

        Person person = personRepository.getOne(patient.get().getPersonId());
        PersonContact personContact = personContactRepository.findByPersonId(person.getId()).get();
        PatientDTO patientDTO = patientMapper.toPatientDTO(person, personContact, patient.get());

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

    /*private List<PersonRelativeDTO> getPersonRelatives(List<PersonRelative> personRelatives,PatientDTO patientDTO, List<PersonRelativeDTO> personRelativeDTOs){
        personRelatives.forEach(personRelative -> {
            PersonRelativeDTO personRelativesDTO = personRelativeMapper.toPersonRelativeDTO(personRelative);
            personRelativeDTOs.add(personRelativesDTO);
        });
        patientDTO.setPersonRelativeDTOs(personRelativeDTOs);

    }*/

    /*public Boolean archivePatient(String HospitalNumber, Byte archive) {
        Optional<Patient> patient1 = this.patientRepository.findByHospitalNumber(HospitalNumber);

        Patient patient = patient1.get();

        if (patient.getArchive() != 1)
            throw new RecordNotFoundException();

        patient.setArchive(archive);

        this.patientRepository.save(patient);
        return archive != patient.getArchive();


    }*/

    public Person updatePatient(PatientDTO patientDTO) {
        //Creating a patient object
        Optional<Patient> patient1 = this.patientRepository.findByHospitalNumber(patientDTO.getHospitalNumber());
        if (!patient1.isPresent()) notExit(Patient.class, "Hospital Number", patientDTO.getHospitalNumber());

        final Person person = patientMapper.toPerson(patientDTO);
        final Person updatedPerson = this.personRepository.save(person);
        log.info("Updating person ... " + updatedPerson);

        final PersonContact personContact = patientMapper.toPersonContact(patientDTO);
        personContact.setPersonByPersonId(updatedPerson);
        personContact.setPersonId(updatedPerson.getId());
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

    //TOdo add a method to get patient Relative - to avoid duplicate codes

}
