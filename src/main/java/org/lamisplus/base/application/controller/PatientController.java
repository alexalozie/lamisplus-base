package org.lamisplus.base.application.controller;

import lombok.RequiredArgsConstructor;
import org.lamisplus.base.application.domiain.dto.HeaderUtil;
import org.lamisplus.base.application.domiain.dto.PatientDTO;
import org.lamisplus.base.application.domiain.model.Patient;
import org.lamisplus.base.application.domiain.model.Person;
import org.lamisplus.base.application.repository.*;
import org.lamisplus.base.application.service.PatientService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PersonRepository personRepository;
    private final PersonContactRepository personContactRepository;
    private final PersonRelativeRepository personRelativeRepository;
    private final PatientRepository patientRepository;
    private final PatientService patientService;
    private final ServiceEnrollmentRepository patientServiceEnrollmentRepository;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll() {
        return ResponseEntity.ok(this.patientService.getAllPatient());
    }

    @GetMapping("/{HospitalNo}")
    public PatientDTO findOne(@PathVariable String HospitalNo) {
        return this.patientService.getPatientByHospitalNo(HospitalNo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Person> save(@RequestBody PatientDTO patientDTO) throws URISyntaxException {
        Person person = this.patientService.save(patientDTO);
        return ResponseEntity.created(new URI("/api/patient/" + ((Person) person).getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(person.getId()))).body(person);
    }


    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient save(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }
    */

    @PutMapping("/{id}")
    public Patient update(@RequestBody Patient patient, @PathVariable Long id) {
        if (patient.getId() != (id)) {
            throw new RecordIdMismatchException(id);
        }
        patientRepository.findById(id)
                .orElseThrow(RecordNotFoundException::new);
        return patientRepository.save(patient);
    }

   /* @DeleteMapping("/{hospitalNumber}")
    public Boolean delete(@PathVariable String hospitalNumber, @RequestParam Byte archive) throws RecordNotFoundException {
        return this.patientService.archivePatient(hospitalNumber, archive);
    }*/
}
