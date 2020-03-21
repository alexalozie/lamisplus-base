package org.lamisplus.modules.base.base.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.lamisplus.modules.base.base.apierror.RecordExistException;
import org.lamisplus.modules.base.base.domiain.dto.BadRequestAlertException;
import org.lamisplus.base.module.domiain.dto.PatientDTO;
import org.lamisplus.modules.base.base.domiain.dto.PersonRelativeDTO;
import org.lamisplus.base.module.domiain.mapper.PatientMapper;
import org.lamisplus.modules.base.base.domiain.mapper.PersonRelativeMapper;
import org.lamisplus.modules.base.base.domiain.model.Patient;
import org.lamisplus.modules.base.base.domiain.model.Person;
import org.lamisplus.modules.base.base.domiain.model.PersonContact;
import org.lamisplus.modules.base.base.domiain.model.PersonRelative;
import org.lamisplus.modules.base.base.repository.PatientRepository;
import org.lamisplus.modules.base.base.repository.PersonContactRepository;
import org.lamisplus.modules.base.base.repository.PersonRelativeRepository;
import org.lamisplus.modules.base.base.repository.PersonRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PatientService {

    // Declaring variable used in the PatientServices
    private static final String ENTITY_NAME = "patient";
    private final PatientRepository patientRepository;
    private final PersonRepository personRepository;
    private final PersonContactRepository personContactRepository;
    private final PersonRelativeRepository personRelativeRepository;
    private final PatientMapper patientMapper;
    private final PersonRelativeMapper personRelativeMapper;

    //Constructor initialization of Local variable taken care of by lombok


    private static Object exist(Patient patient) {
        throw new RecordExistException(Patient.class, "Hospital Number", patient.getHospitalNumber());
    }


    private static Patient notExit() {
        throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id is null");
    }

    //Checking for null Value
    public Object process(Object o) throws NullPointerException {
        if (o == null) {
            throw new NullPointerException(o.toString() + "in empty/invalid");
        } else {
            return o;
        }
    }

    /**
     * Saving a patient
     */
    public Person save(PatientDTO patientDTO) {
        //Creating a patient object
        Optional<Patient> patient1 = this.patientRepository.findByHospitalNumber(patientDTO.getHospitalNumber());
        patient1.map(PatientService::exist);

        final Person person = patientMapper.toPerson(patientDTO);
        final Person createdPerson = this.personRepository.save(person);

        final PersonContact personContact = patientMapper.toPersonContact(patientDTO);
        personContact.setPersonByPersonId(createdPerson);
        personContact.setPersonId(createdPerson.getId());
        //this.personContactRepository.save(personContact);
        LOG.info("SAVING person ... " + this.personContactRepository.save(personContact));


        if (patientDTO.getPersonRelativeDTOs()!= null) {
            final List<PersonRelative> personRelatives = new ArrayList<>();
            patientDTO.getPersonRelativeDTOs().forEach(personRelativeDTO -> {
                final PersonRelative personRelative = personRelativeMapper.toPersonRelative(personRelativeDTO);
                personContact.setPersonByPersonId(createdPerson);
                personRelatives.add(personRelative);
            });
            this.personRelativeRepository.save(personRelatives);
            LOG.info("SAVING person relatives ... " + this.personRelativeRepository.save(personRelatives));
        }
        //creating a patient object
        final Patient patient = patientMapper.toPatient(patientDTO);
        patient.setPersonByPersonId(createdPerson);
        patient.setPersonId(createdPerson.getId());
        //this.patientRepository.save(patient);
        LOG.info("SAVING patient relatives ... " + this.patientRepository.save(patient));

        return person;
    }

    public List<PatientDTO> getAllPatient() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOs = new ArrayList<>();
        List<PersonRelativeDTO> personRelativeDTOs = new ArrayList<>();

        patients.forEach(patient -> {
            Person person = personRepository.getOne(patient.getPersonId());
            LOG.info("PERSON ID IS ..." + person.getId());
            PersonContact personContact = personContactRepository.findByPersonId(person.getId()).get();
            PatientDTO patientDTO = patientMapper.toPatientDTO(person, personContact, patient);
            System.out.println("patientDTO date is ..." + patientDTO.getDateRegistration());
            List<PersonRelative> personRelatives = personRelativeRepository.findByPersonId(person.getId());
            if (personRelatives.size() > 0) {
                personRelatives.forEach(personRelative -> {
                    PersonRelativeDTO personRelativesDTO = personRelativeMapper.toPersonRelativeDTO(personRelative);
                    personRelativeDTOs.add(personRelativesDTO);
                });
                patientDTO.setPersonRelativeDTOs(personRelativeDTOs);
            }
            patientDTOs.add(patientDTO);
        });
        return patientDTOs;
    }

    public PatientDTO getPatientByHospitalNo(String patientNumber) {
        Optional<Patient> patient = this.patientRepository.findByHospitalNumber(patientNumber);
        if(!patient.isPresent()) PatientService.notExit();
        Person person = personRepository.getOne(patient.get().getPersonId());
        PersonContact personContact = personContactRepository.findByPersonId(person.getId()).get();
        PatientDTO patientDTO = patientMapper.toPatientDTO(person, personContact, patient.get());

        List<PersonRelativeDTO> personRelativeDTOs = new ArrayList<>();
        List<PersonRelative> personRelatives = personRelativeRepository.findByPersonId(person.getId());
        if (personRelatives.size() > 0) {
            personRelatives.forEach(personRelative -> {
                PersonRelativeDTO personRelativesDTO = personRelativeMapper.toPersonRelativeDTO(personRelative);
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

        final Person person = patientMapper.toPerson(patientDTO);
        final Person updatedPerson = this.personRepository.save(person);
        LOG.info("Updating person ... " + updatedPerson);

        final PersonContact personContact = patientMapper.toPersonContact(patientDTO);
        personContact.setPersonByPersonId(updatedPerson);
        personContact.setPersonId(updatedPerson.getId());
        final PersonContact updatedContact = this.personContactRepository.save(personContact);

        LOG.info("Updating person Contact ... " + updatedContact);

        final List<PersonRelative> personRelatives = new ArrayList<>();
        patientDTO.getPersonRelativeDTOs().forEach(personRelativeDTO -> {
            final PersonRelative personRelative = personRelativeMapper.toPersonRelative(personRelativeDTO);
            personContact.setPersonByPersonId(updatedPerson);
            personRelatives.add(personRelative);
        });
        final List<PersonRelative> updatedRelative = this.personRelativeRepository.save(personRelatives);
        LOG.info("Updating person relatives ... " + updatedRelative);


        //creating a patient object
        final Patient patient = patientMapper.toPatient(patientDTO);
        patient.setPersonByPersonId(updatedPerson);
        patient.setPersonId(updatedPerson.getId());
        final Patient updatedPatient = this.patientRepository.save(patient);
        LOG.info("Updating patient... " + updatedPatient);

        return person;
    }

    //TOdo add a method to get patient Relative - to avoid duplicate codes

}
