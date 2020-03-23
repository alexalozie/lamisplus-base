package org.lamisplus.modules.base.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.lamisplus.modules.base.domain.dto.EncounterDTO;
import org.lamisplus.modules.base.domain.entities.Person;
import org.lamisplus.modules.base.repository.*;
import org.lamisplus.modules.base.service.EncounterService;
import org.lamisplus.modules.base.service.PatientService;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.lamisplus.modules.base.domain.dto.PatientDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    private final String ENTITY_NAME = "Patient";
    private final PatientRepository patientRepository;
    private final PatientService patientService;
    private final EncounterService encounterService;

    @GetMapping
    public Iterable findAll() {
        return this.patientService.getAllPatient();
    }

    @GetMapping("/{hospitalNumber}")
    public PatientDTO getPatientByHospitalNumber(@PathVariable String hospitalNumber) {
        return this.patientService.getPatientByHospitalNumber(hospitalNumber);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Person> save(@RequestBody PatientDTO patientDTO) throws URISyntaxException {
        Person person = this.patientService.save(patientDTO);
        return ResponseEntity.created(new URI("/api/patient/" + person.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(person.getId()))).body(person);
    }

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient save(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }
    */
    @PutMapping
    public Person update(@RequestBody PatientDTO patientDTO) throws RecordNotFoundException {
        return this.patientService.update(patientDTO);
    }

    //GETTING LATEST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION for a particular patient
    @GetMapping("/{patientId}/encounter/{serviceName}/{formName}/{visitId}")
    public ResponseEntity<EncounterDTO> getEncounterByVisitId(@PathVariable Long patientId, @PathVariable String serviceName,
                                                                 @PathVariable String formName, @PathVariable Long visitId) throws URISyntaxException {
        return ResponseEntity.ok(this.encounterService.getEncounterByVisitId(patientId, serviceName, formName, visitId));
    }

    //GETTING LATEST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION for a particular patient
    @GetMapping("/{patientId}/encounter/{serviceName}/{formName}")
    public ResponseEntity<List<EncounterDTO>> getSortedEncounter(@PathVariable Long patientId, @PathVariable String serviceName,
                                                                 @PathVariable String formName, @RequestParam(required = false) String sortOrder,
                                                                 @RequestParam (required = false) String sortField, @RequestParam(required = false) Integer limit) throws URISyntaxException {
        return ResponseEntity.ok(this.encounterService.getSortedEncounter(patientId, serviceName, formName, sortField, sortOrder, limit));
    }
   /* @DeleteMapping("/{HospitalNo}")
    public Boolean archivePatient(@PathVariable String hospitalNumber, @RequestParam Byte archive) throws RecordNotFoundException {
        return this.patientService.archivePatient(hospitalNumber, archive);
    }*/
}
