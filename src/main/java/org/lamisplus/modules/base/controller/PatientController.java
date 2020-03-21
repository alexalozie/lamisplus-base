package org.lamisplus.modules.base.controller;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.base.domain.entities.Person;
import org.lamisplus.modules.base.repository.*;
import org.lamisplus.modules.base.service.PatientService;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.lamisplus.modules.base.domain.dto.PatientDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    private final String ENTITY_NAME = "Patient";
    private final PatientRepository patientRepository;
    private final PatientService patientService;

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
    public Person updatePatient(@RequestBody PatientDTO patientDTO) throws RecordNotFoundException {
        return this.patientService.updatePatient(patientDTO);
    }

   /* @DeleteMapping("/{HospitalNo}")
    public Boolean archivePatient(@PathVariable String hospitalNumber, @RequestParam Byte archive) throws RecordNotFoundException {
        return this.patientService.archivePatient(hospitalNumber, archive);
    }*/
}
