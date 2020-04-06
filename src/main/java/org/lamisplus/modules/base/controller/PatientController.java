package org.lamisplus.modules.base.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.lamisplus.modules.base.domain.dto.*;
import org.lamisplus.modules.base.domain.entities.Patient;
import org.lamisplus.modules.base.domain.entities.Person;
import org.lamisplus.modules.base.repository.*;
import org.lamisplus.modules.base.service.EncounterService;
import org.lamisplus.modules.base.service.PatientService;
import org.lamisplus.modules.base.service.VisitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    private final String ENTITY_NAME = "Patient";
    private final PatientService patientService;
    private final VisitService visitService;


    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(this.patientService.getAllPatients());
    }

    @GetMapping("/{hospitalNumber}")
    public ResponseEntity<PatientDTO> getPatientByHospitalNumber(@PathVariable String hospitalNumber) {
        return ResponseEntity.ok(this.patientService.getPatientByHospitalNumber(hospitalNumber));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Person> save(@RequestBody PatientDTO patientDTO) throws URISyntaxException {
        Person person = this.patientService.save(patientDTO);
        return ResponseEntity.created(new URI("/api/patient/" + person.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(person.getId()))).body(person);
    }

    @PutMapping("/{hospitalNumber}")
    public Person update(@PathVariable String hospitalNumber, @RequestBody PatientDTO patientDTO) throws RecordNotFoundException {
        return this.patientService.update(hospitalNumber, patientDTO);
    }

    @GetMapping("/{id}/encounters/{programCode}/{formCode}/{visitId}")
    public ResponseEntity<EncounterDTO> getEncountersByPatientIdAndVisitId(@PathVariable Long id, @PathVariable String programCode,
                                                                           @PathVariable String formCode, @PathVariable Long visitId) throws URISyntaxException {
        return ResponseEntity.ok(this.patientService.getEncountersByPatientIdAndVisitId(id, programCode, formCode, visitId));
    }

    @GetMapping("/{id}/encounter/{programCode}/{formCode}")
    public ResponseEntity<List<EncounterDTO>> getEncountersByPatientId(@PathVariable Long id, @PathVariable String programCode,
                                                                       @PathVariable String formCode, @RequestParam(required = false) String sortOrder,
                                                                       @RequestParam (required = false) String sortField, @RequestParam(required = false) Integer limit) throws URISyntaxException {
        return ResponseEntity.ok(this.patientService.getEncountersByPatientId(id, programCode, formCode, sortField, sortOrder, limit));
    }

    @ApiOperation(value="getVisitByPatientIdAndVisitDate", notes = "patientId= required, dateStart=optional, dateEnd=optional\n\n" +
            "Example - /api/visits/patient/20/?dateStart=02-03-2020")
    @GetMapping("/{id}/visits")
    public ResponseEntity<List<VisitDTO>> getVisitByPatientIdAndVisitDate(@PathVariable Optional<Long> id, @RequestParam(required = false) Optional<String> dateStart,
                                                                          @RequestParam(required = false) Optional <String> dateEnd) {
        return ResponseEntity.ok(visitService.getVisitByPatientIdAndVisitDate(id,dateStart,dateEnd));
    }

    //GETTING LATEST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION all patients)
    @ApiOperation(value="getEncounterByDate", notes = " programCode= required, formCode=required, dateStart=optional, dateEnd=optional\n\n" +
            "Example - api/encounters/{programCode}/{formCode}?dateStart=01-01-2020&dateEnd=01-04-2020")
    @GetMapping("/{id}/encounters/{programCode}/{formCode}")
    public ResponseEntity<List<EncounterDTO>> getEncountersByPatientIdAndDateEncounter(@PathVariable Long id, @PathVariable String programCode, @PathVariable String formCode,
                                                                                       @RequestParam(required = false) Optional<String> dateStart,
                                                                                       @RequestParam(required = false) Optional<String> dateEnd) throws URISyntaxException {
        List<EncounterDTO> encounterDTOS = this.patientService.getEncountersByPatientIdAndDateEncounter(id, programCode, formCode, dateStart, dateEnd);
        return ResponseEntity.ok(encounterDTOS);
    }

    //GETTING ENCOUNTER BY PATIENT ID
    @ApiOperation(value="getAllEncountersByPatientId", notes = " id=required\n\n" +
            "Example - /api/encounters/20")
    @GetMapping("/{id}/encounters")
    //@ResponseStatus(reason = "Some parameters are invalid")
    public ResponseEntity<List<EncounterDTO>> getAllEncounterByPatientId(@PathVariable Long id) throws BadRequestAlertException {
        return ResponseEntity.ok(this.patientService.getAllEncountersByPatientId(id));
    }

/*    @ApiOperation(value="getFormsByPatientId", notes = " id=required, formCode=required\n\n")
    @GetMapping("/{id}/{formCode}")
    public ResponseEntity<List<EncounterDTO>> getFormsByPatientId(@PathVariable Long id, @PathVariable String formCode) throws BadRequestAlertException {
        return ResponseEntity.ok(this.patientService.getFormsByPatientId(id, formCode));
    }*/

    @ApiOperation(value="getFormsByPatientId", notes = " id=required, formCode=required\n\n")
    @GetMapping("/{id}/{formCode}")
    public ResponseEntity<List<Object>> testing(@PathVariable Long id, @PathVariable String formCode) throws BadRequestAlertException {
        return ResponseEntity.ok(this.patientService.testing(id, formCode));
    }


/*    @ApiOperation(value="getFormsByPatientId", notes = " id=required, formCode=required\n\n")
    @GetMapping("/{id}/form")
    public ResponseEntity<List<Form>> getFormsByPatientId(@PathVariable Long id) throws BadRequestAlertException {
        return ResponseEntity.ok(this.patientService.getFormsByPatientId(id, formCode));
    }*/

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id, @RequestBody Patient patient) throws RecordNotFoundException {
        return this.patientService.delete(id, patient);
    }
}
