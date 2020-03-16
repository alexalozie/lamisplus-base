package org.lamisplus.base.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.lamisplus.base.application.controller.RecordNotFoundException;
import org.lamisplus.base.application.domiain.dto.BadRequestAlertException;
import org.lamisplus.base.application.domiain.dto.EncounterDTO;
import org.lamisplus.base.application.domiain.dto.LabTestDTO;
import org.lamisplus.base.application.domiain.mapper.EncounterMapper;
import org.lamisplus.base.application.domiain.mapper.LabTestMapper;
import org.lamisplus.base.application.domiain.model.*;
import org.lamisplus.base.application.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
    private final EncounterRepository patientEncounterRepository;
    private final UserRepository userRepository;
    private final ClinicianPatientRepository clinicianPatientRepository;
    private final ApplicationCodesetRepository applicationCodesetRepository;
    private final LabTestRepository labTestRepository;
    private final DrugRepository drugRepository;
    private final LabTestGroupRepository labTestGroupRepository;
    private final EncounterMapper encounterMapper;
    private final LabTestMapper labTestMapper;


    private static Object exist(Encounter o) {
        throw new BadRequestAlertException("Encounter  Already Exist", ENTITY_NAME, "id Already Exist");
    }

    private static Encounter notExit() {
        throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id is null");
    }

    public Encounter save(EncounterDTO encounterDTO) {
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

    public EncounterDTO singleEncounter(Long patientId, String serviceName, String formName, LocalDate dateEncounter){
        //Patient patient1 = this.patientRepository.getOne(encounterRequest.getPatientId());
        //Check if a form by form name exist
        //Form form = formRepository.findByName(encounterRequest.getFormName());
        //Checking if service name exist
        //Service service = this.servicesRepository.findByServiceName(encounterRequest.getServiceName());
        //Checking is the patient has been checked in by visit id
        //Visit visit = this.visitRepository.getOne(encounterRequest.getVisitId());
        //check if an encounter already exist with
        //findBy PatientId And ServiceName And FormName And VisitId

        Optional<Encounter> encounters = this.encounterRepository.findByPatientIdAndServiceNameAndFormNameAndDateEncounter(patientId, serviceName, formName, dateEncounter);
        EncounterDTO encounterDTO1 = encounterMapper.toEncounterDTO(encounters.get());
        return encounterDTO1;
    }

    //Getting a single encounter for a patient
    public EncounterDTO getByPatientIdServiceNameFormNameDate(Long PatientId, String ServiceName, String FormName, LocalDate localDate) {

        Optional<Encounter> patientEncounter = this.encounterRepository.findByPatientIdAndServiceNameAndFormNameAndDateEncounter(PatientId, ServiceName, FormName, localDate);
        //patientEncounter.map(EncounterService::exist);
        if (!patientEncounter.isPresent())
            throw new RecordNotFoundException();


        Encounter encounter = patientEncounter.get();
        Patient patient = this.patientRepository.findById(encounter.getPatientId()).get();

        /*if (patient.getArchive() == 0)
            throw new RecordNotFoundException();
*/
        Person person = this.personRepository.findById(patient.getPersonId()).get();

        final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, encounter);

        LOG.info("GETTING encounter by Service, Form,  Date... " + encounterDTO);

        return encounterDTO;

    }


    public List<EncounterDTO> getAll() {
        List<EncounterDTO> encounterDTOS = new ArrayList();
        List <Encounter> allEncounter = encounterRepository.findAll();
        allEncounter.forEach(SingleEncounter -> {

            Patient patient = this.patientRepository.findById(SingleEncounter.getPatientId()).get();
            Person person = this.personRepository.findById(patient.getPersonId()).get();

            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, SingleEncounter);

            LOG.info("GETTING encounter in List 12... " + encounterDTO);

            encounterDTOS.add(encounterDTO);


        });

        return encounterDTOS;

    }

    public List<EncounterDTO> getAllByPatientId(Long patientId) {
        List<EncounterDTO> encounterDTOS = new ArrayList();

        List<Encounter> tempEncounter = encounterRepository.findBypatientId(patientId);

        if (tempEncounter.size() < 1)
            throw new RecordNotFoundException();

        tempEncounter.forEach(OnePatientEncounter -> {
            Patient patient = this.patientRepository.findById(OnePatientEncounter.getPatientId()).get();

            Person person = this.personRepository.findById(patient.getPersonId()).get();

            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, OnePatientEncounter);
            LOG.info("GETTING encounter in List by Pid 123456... " + encounterDTO);

            encounterDTOS.add(encounterDTO);
        });

        return encounterDTOS;
    }

    public EncounterDTO getByEncounterId(Long id) {
        Optional<Encounter> patientEncounter = this.encounterRepository.findById(id);

        if (!patientEncounter.isPresent())
            throw new RecordNotFoundException();

        Encounter encounter = patientEncounter.get();

        Patient patient = this.patientRepository.findById(encounter.getPatientId()).get();

        Person person = this.personRepository.findById(patient.getPersonId()).get();

        final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, encounter);

        return encounterDTO;
    }

    //UPDATING AN ENCOUNTER
    public Encounter updateEncounter(Long encounterId, Object formData) {
        LOG.info(encounterId + " EncounterDTO for Updating form Data -" + formData);
        Optional<Encounter> encounter = this.encounterRepository.findById(encounterId);
        encounter.orElseGet(EncounterService::notExit);

        //getting a single encounter
        Encounter encounter1 = encounter.get();

        Patient patient = this.patientRepository.findById(encounter1.getPatientId()).get();

/*
        if (patient.getArchive() == 0)
            throw new RecordNotFoundException();
*/

        //SETTING NEW ENCOUNTERS FOR UPDATE
        encounter1.setFormData(formData);


        //UPDATING THE ENCOUNTER
        this.encounterRepository.save(encounter1);

        return encounter1;
    }


    public ClinicianPatient assignClinican(ClinicianPatient clinicianPatient) {
        LOG.info("Saving Assigned Clinician to Patient " + clinicianPatient.getPatientId() + "visitid = " + clinicianPatient.getVisitId() + clinicianPatient.getClinicanByUserId() + clinicianPatient.getAppCodesetId());

        ClinicianPatient cp = this.clinicianPatientRepository.save(clinicianPatient);

        LOG.info("Saving Assigned Clinician to Patient " + cp);

        return clinicianPatient;

    }

    public List<LabTestGroup> getAllLabTestGroup() {

        return this.labTestGroupRepository.findAll();
    }

    public List<Drug> getAllDrug() {

        return this.drugRepository.findAll();
    }


    //Getting a single encounter for a patient
    //GETTING LATEST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION)
    public EncounterDTO getLatestEncounter(Long PatientId, String ServiceName, String FormName) {

        Optional<Encounter> patientEncounter = this.encounterRepository.findFirstByPatientIdAndServiceNameAndFormNameOrderByDateEncounterDesc(PatientId, ServiceName, FormName);
        //patientEncounter.map(EncounterService::exist);

        if (!patientEncounter.isPresent())
            throw new RecordNotFoundException();

        Encounter encounter = patientEncounter.get();
        Patient patient = this.patientRepository.findById(encounter.getPatientId()).get();

        //Creating a person object
        Person person = this.personRepository.findById(patient.getPersonId()).get();

        final EncounterDTO encounterDTO1 = encounterMapper.toEncounterDTO(person, patient, encounter);


        LOG.info("GETTING encounter Latest 12... " + encounterDTO1);
        //issues may come up with wrong form name
        return encounterDTO1;

    }

    public List<EncounterDTO> getAllLatestEncounter(LocalDate DateEncounter, String serviceName, String formName) {
        List<EncounterDTO> encounterDTOS = new ArrayList();

        List<Encounter> tempEncounter = encounterRepository.findAllByServiceNameAndFormNameAndDateEncounterOrderByDateEncounterDesc(serviceName, formName, DateEncounter);

        if (tempEncounter.size() < 1)
            throw new RecordNotFoundException();


        tempEncounter.forEach(OnePatientEncounter -> {
            Patient patient = this.patientRepository.findById(OnePatientEncounter.getPatientId()).get();
/*

            if (patient.getArchive() == 0)
                return;
*/

            Person person = this.personRepository.findById(patient.getPersonId()).get();
            final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, OnePatientEncounter);

            LOG.info("GETTING encounter in List by Pid 12... " + encounterDTO);

            encounterDTOS.add(encounterDTO);
        });

        return encounterDTOS;
    }

    //List of Lab Test
    public List<LabTestDTO> getAllLabTest(Long labTestCategoryId) {
        List<LabTestDTO> labTestDTOS = new ArrayList();
        this.labTestRepository.findAllByLabTestCategoryId(labTestCategoryId).forEach(singleTest -> {
            LabTestDTO labTestDTO = labTestMapper.toLabTest(singleTest);

            /*labTestDTO.setId(singleTest.getId());
            labTestDTO.setDescription(singleTest.getDescription());
            labTestDTO.setUnitMeasurement(singleTest.getUnitMeasurement());
*/
            labTestDTOS.add(labTestDTO);
            LOG.info("LabTest Description is " + singleTest.getDescription());

        });
        return labTestDTOS;
    }
}
