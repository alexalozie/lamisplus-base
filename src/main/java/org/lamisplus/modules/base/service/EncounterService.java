package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entities.*;
import org.lamisplus.modules.base.domain.dto.EncounterDTO;

import org.lamisplus.modules.base.domain.mapper.EncounterMapper;
import org.lamisplus.modules.base.repository.*;

import org.springframework.transaction.annotation.Transactional;

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
    private final PersonRepository personRepository;
    private final ProgramRepository programRepository;
    private final EncounterRepository encounterRepository;
    private final VisitRepository visitRepository;
    private final FormRepository formRepository;
    private final EncounterMapper encounterMapper;



    public List<EncounterDTO> getAllEncounters() {
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

    public EncounterDTO getEncounter(Long id) {
        Optional<Encounter> encounterOptional = encounterRepository.findById(id);

        Patient patient = this.patientRepository.findById(encounterOptional.get().getPatientId()).get();
        Person person = this.personRepository.findById(patient.getPersonId()).get();

        final EncounterDTO encounterDTO = encounterMapper.toEncounterDTO(person, patient, encounterOptional.get());

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

        Optional<Program> program = this.programRepository.findByProgramCode(encounterDTO.getProgramCode());
        if(!program.isPresent()){
            throw new EntityNotFoundException(Program.class,"Program Name", encounterDTO.getProgramCode());
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

    public Boolean delete(Long id, Encounter encounter) {
        return true;
    }
}
