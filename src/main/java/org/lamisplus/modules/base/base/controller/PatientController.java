package org.lamisplus.modules.base.base.controller;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.base.base.domiain.dto.HeaderUtil;
import org.lamisplus.base.module.domiain.dto.PatientDTO;
import org.lamisplus.modules.base.base.domiain.model.Patient;
import org.lamisplus.modules.base.base.domiain.model.Person;
import org.lamisplus.base.module.repository.*;
import org.lamisplus.modules.base.base.service.PatientService;
import org.lamisplus.modules.base.base.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll() {
        return ResponseEntity.ok(this.patientService.getAllPatient());
    }

    @GetMapping("/{hospitalNumber}")
    public PatientDTO findOne(@PathVariable String hospitalNumber) {
        return this.patientService.getPatientByHospitalNo(hospitalNumber);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Person> savePatient(@RequestBody PatientDTO patientDTO) throws URISyntaxException {
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
    public Patient updatePatient(@RequestBody Patient patient, @PathVariable Long id) {
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
