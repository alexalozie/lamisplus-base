package org.lamisplus.modules.base.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.entities.Encounter;
import org.lamisplus.modules.base.service.EncounterService;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.dto.EncounterDTO;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/encounters")
@Slf4j
@RequiredArgsConstructor
public class EncounterController {
    private static String ENTITY_NAME = "encounter";
    private final EncounterService encounterService;

    //SAVING AN ENCOUNTER (ALL ENCOUNTER - VITALS, CONSULTATION, LAB TEST ORDER)
    @PostMapping
    public ResponseEntity<Encounter> saveEncounter(@RequestBody EncounterDTO encounterDTO) throws URISyntaxException {
        Encounter encounter = encounterService.saveConsultation(encounterDTO);
        return ResponseEntity.created(new URI("/api/encounter/" + encounter.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(encounter.getId()))).body(encounter);
    }

    //FOR SAVING ALL CONSULTATION FORM......
    /*@PostMapping("/{serviceName}/{formName}/{patientId}")
    public ResponseEntity<Encounter> saveEncounter(@RequestBody EncounterDTO encounterDTO, @PathVariable(required = false) String serviceName, @PathVariable(required = false) String formName, @PathVariable(required = false) Long patientId) throws URISyntaxException {

        Encounter encounter = encounterService.saveConsultation(encounterDTO);
        return ResponseEntity.created(new URI("/api/encounter/" + encounter.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(encounter.getId()))).body(encounter);
    }
*/

    //GET ALL ENCOUNTER
    @GetMapping
    public ResponseEntity<List<EncounterDTO>> getAllEncounter() {
        return ResponseEntity.ok(this.encounterService.getAllEncounter());

    }

    //GETTING LATEST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION)
    @GetMapping("/{serviceName}/{formName}/{patientId}/{dateEncounter}")
    public ResponseEntity<EncounterDTO> getSingle(@PathVariable Long patientId, @PathVariable String serviceName, @PathVariable String formName, @RequestParam(required = false) String dateEncounter) throws URISyntaxException {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        if (dateEncounter == null || dateEncounter.isEmpty() || dateEncounter.equals("")) {
            dateEncounter = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } else {
            // Converting 'dd-MM-yyyy' String format to LocalDate
            localDate = LocalDate.parse(dateEncounter, formatter);
        }
        log.info("Date is " + dateEncounter);
        return ResponseEntity.ok(this.encounterService.getByPatientIdServiceNameFormNamelocalDate(patientId, serviceName, formName, localDate));
    }

    //GETTING LATEST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION for a particular patient
    @GetMapping("/{serviceName}/{formName}/{patientId}/today")
    public ResponseEntity<EncounterDTO> getLatestSingleEncounter(@PathVariable Long patientId, @PathVariable String serviceName, @PathVariable String formName) throws URISyntaxException {
        return ResponseEntity.ok(this.encounterService.getLatestEncounter(patientId, serviceName, formName));
    }

    //GETTING LAST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION for a particular patient
    @GetMapping("/{serviceName}/{formName}/{patientId}/lastEncounter")
    public ResponseEntity<EncounterDTO> getLastEncounter(@PathVariable Long patientId, @PathVariable String serviceName, @PathVariable String formName) throws URISyntaxException {
        return ResponseEntity.ok(this.encounterService.getLastEncounter(patientId, serviceName, formName));
    }

    //GETTING FIRST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION for a particular patient
    @GetMapping("/{serviceName}/{formName}/{patientId}/firstEncounter")
    public ResponseEntity<EncounterDTO> getFirstEncounter(@PathVariable Long patientId, @PathVariable String serviceName, @PathVariable String formName) throws URISyntaxException {
        return ResponseEntity.ok(this.encounterService.getFirstEncounter(patientId, serviceName, formName));
    }

    //GETTING ENCOUNTER DATE RANGE(DRUG ORDER, VITALS, LAB TEST, CONSULTATION for a particular patient
    @GetMapping("/{serviceName}/{formName}/{patientId}/{firstDate}/{lastDate}")
    public ResponseEntity<List<EncounterDTO>> getByDateRange(@PathVariable Long patientId, @PathVariable String serviceName, @PathVariable String formName, @PathVariable String firstDate, @PathVariable String lastDate) throws URISyntaxException {
        return ResponseEntity.ok(this.encounterService.getDateByRange(patientId, serviceName, formName, firstDate, lastDate));
    }

    //GETTING LATEST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION all patients)
    @GetMapping("/{serviceName}/{formName}")
    public ResponseEntity<List<EncounterDTO>> getAllLatestEncounter(@PathVariable String serviceName, @PathVariable String formName) throws URISyntaxException {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        log.info("Date is " + date);
        log.info("All Encounter " + serviceName + " and " + formName + " Today is " + localDate);

        return ResponseEntity.ok(this.encounterService.getAllLatestEncounter(localDate, serviceName, formName));
    }

    //GETTING ENCOUNTER BY ID
   /* @GetMapping("/id")
    public ResponseEntity<EncounterDTO> getPatientEncounter(@RequestParam Long encounterId) {
        return ResponseEntity.ok(this.encounterService.getByEncounterId(encounterId));
    }*/

    //GETTING ENCOUNTER BY PATIENT ID
    @GetMapping("/{patientId}")
    //@ResponseStatus(reason = "Some parameters are invalid")
    public ResponseEntity<List<EncounterDTO>> getAllEncounterByPatientId(@PathVariable Long patientId) throws BadRequestAlertException {
        return ResponseEntity.ok(this.encounterService.getAllByPatientId(patientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Encounter> updateEncounter(@RequestBody EncounterDTO encounterDTO) throws URISyntaxException {
        Encounter encounter = this.encounterService.updateEncounter(encounterDTO);
        return ResponseEntity.created(new URI("/api/encounter/" + encounter.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(encounter.getId()))).body(encounter);
    }
}
