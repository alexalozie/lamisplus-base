package org.lamisplus.modules.base.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.dto.*;
import org.lamisplus.modules.base.domain.entity.Patient;
import org.lamisplus.modules.base.domain.entity.Person;
import org.lamisplus.modules.base.service.PatientService;
import org.lamisplus.modules.base.service.VisitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
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

    @PutMapping("/{id}")
    public Person update(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        return this.patientService.update(id, patientDTO);
    }

  /*  @GetMapping("/{id}/encounters/{programCode}/{formCode}/{visitId}")
    public ResponseEntity<EncounterDTO> getEncountersByPatientIdAndVisitId(@PathVariable Long id, @PathVariable String programCode,
                                                                           @PathVariable String formCode, @PathVariable Long visitId) throws URISyntaxException {
        return ResponseEntity.ok(this.patientService.getEncountersByPatientIdAndVisitId(id, programCode, formCode, visitId));
    }
*/
    @GetMapping("/{id}/encounters/{formCode}")
    public ResponseEntity<List> getEncountersByPatientIdAndFormCode(@PathVariable Long id,
                                                                       @PathVariable String formCode, @RequestParam(required = false) String sortOrder,
                                                                       @RequestParam (required = false) String sortField, @RequestParam(required = false) Integer limit) throws URISyntaxException {
        return ResponseEntity.ok(this.patientService.getEncountersByPatientIdAndFormCode(id, formCode, sortField, sortOrder, limit));
    }
    @GetMapping("/{id}/encounters/programCodeExclusionList")
    public ResponseEntity<List> getEncountersByPatientIdAndProgramCodeExclusionList(@PathVariable Long id, @RequestParam(required = false) List<String> programCodeExclusionList) throws URISyntaxException {
        return ResponseEntity.ok(this.patientService.getEncountersByPatientIdAndProgramCodeExclusionList(id, programCodeExclusionList));
    }


    @ApiOperation(value="getVisitByPatientIdAndVisitDate", notes = "patientId= required, dateStart=optional, dateEnd=optional\n\n" +
            "Example - /api/patient/20/visits?dateStart=02-03-2020")
    @GetMapping("/{id}/visits/{dateStart}/{dateEnd}")
    public ResponseEntity<List<VisitDTO>> getVisitByPatientIdAndVisitDate(@PathVariable Optional<Long> id, @ApiParam(defaultValue = "",required = false) @PathVariable(required = false) Optional<String> dateStart,
                                                                          @ApiParam(defaultValue = "",required = false) @PathVariable(required = false) Optional <String> dateEnd) {
        return ResponseEntity.ok(patientService.getVisitByPatientIdAndVisitDate(id,dateStart,dateEnd));
    }

    //GETTING LATEST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION all patients)
    @ApiOperation(value="getEncountersByPatientIdAndDateEncounter", notes = " programCode= required, formCode=required, dateStart=optional, dateEnd=optional\n\n" +
            "Example - api/encounters/{programCode}/{formCode}?dateStart=01-01-2020&dateEnd=01-04-2020")
    @GetMapping("/{id}/encounters/{formCode}/{dateStart}/{dateEnd}")
    public List getEncountersByPatientIdAndDateEncounter(@PathVariable Long id, @PathVariable String formCode,
                                                         @ApiParam(defaultValue = "",required = false) @PathVariable(required = false) Optional<String> dateStart,
                                                         @ApiParam(defaultValue = "",required = false) @PathVariable(required = false) Optional<String> dateEnd) throws URISyntaxException {
        List formDataList = this.patientService.getEncountersByPatientIdAndDateEncounter(id, formCode, dateStart, dateEnd);
        log.info("GETTING encounterList 123 in List by formDataList... " + formDataList);
        return formDataList;
    }

    //GETTING ENCOUNTER BY PATIENT ID
    @ApiOperation(value="getAllEncountersByPatientId", notes = " id=required\n\n" +
            "Example - /api/encounters/20")
    @GetMapping("/{id}/encounters")
    //@ResponseStatus(reason = "Some parameters are invalid")
    public ResponseEntity<List> getAllEncounterByPatientId(@PathVariable Long id) throws BadRequestAlertException {
        return ResponseEntity.ok(this.patientService.getAllEncountersByPatientId(id));
    }

/*    @ApiOperation(value="getFormsByPatientId", notes = " id=required, formCode=required\n\n")
    @GetMapping("/{id}/{formCode}")
    public ResponseEntity<List<EncounterDTO>> getFormsByPatientId(@PathVariable Long id, @PathVariable String formCode) throws BadRequestAlertException {
        return ResponseEntity.ok(this.patientService.getFormsByPatientId(id, formCode));
    }*/


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
