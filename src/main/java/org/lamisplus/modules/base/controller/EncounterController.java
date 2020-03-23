package org.lamisplus.modules.base.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.entities.Encounter;
import org.lamisplus.modules.base.service.EncounterService;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.dto.EncounterDTO;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.lamisplus.modules.base.util.converter.LocalDateConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/encounters")
@Slf4j
@RequiredArgsConstructor
public class EncounterController {
    private static String ENTITY_NAME = "encounter";
    private final EncounterService encounterService;

    //SAVING AN ENCOUNTER (ALL ENCOUNTER - VITALS, CONSULTATION, LAB TEST ORDER)
    @PostMapping
    public ResponseEntity<Encounter> save(@RequestBody EncounterDTO encounterDTO) throws URISyntaxException {
        Encounter encounter = encounterService.saveConsultation(encounterDTO);
        return ResponseEntity.created(new URI("/api/encounter/" + encounter.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(encounter.getId()))).body(encounter);
    }

    //GET ALL ENCOUNTER
    @ApiOperation(value="getAllEncounters", notes = "response is List of EncounterDTO\n\n" +
            "Example - /api/encounters")
    @GetMapping
    public ResponseEntity<List<EncounterDTO>> getAllEncounters() {
        return ResponseEntity.ok(this.encounterService.getAllEncounter());

    }



    //GETTING LATEST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION all patients)
    @ApiOperation(value="getEncounterByDate", notes = " serviceName= required, formName=required, dateStart=optional, dateEnd=optional\n\n" +
            "Example - api/encounters/{serviceName}/{formName}?dateStart=01-01-2020&dateEnd=01-04-2020")
    @GetMapping("/{serviceName}/{formName}")
    public ResponseEntity<List<EncounterDTO>> getSortedEncounter(@PathVariable String serviceName, @PathVariable String formName,
                                                                 @RequestParam(required = false) Optional<String> dateStart,
                                                                 @RequestParam(required = false) Optional<String> dateEnd) throws URISyntaxException {
        List<EncounterDTO> encounterDTOS = this.encounterService.getSortedEncounter(serviceName, formName, dateStart, dateEnd);
        return ResponseEntity.ok(encounterDTOS);
        }

    //GETTING ENCOUNTER BY PATIENT ID
    @ApiOperation(value="getAllEncounterByPatientId", notes = " patientId=required\n\n" +
            "Example - /api/encounters/20")
    @GetMapping("/{patientId}")
    //@ResponseStatus(reason = "Some parameters are invalid")
    public ResponseEntity<List<EncounterDTO>> getAllEncounterByPatientId(@PathVariable Long patientId) throws BadRequestAlertException {
        return ResponseEntity.ok(this.encounterService.getAllByPatientId(patientId));
    }

    @PutMapping
    public ResponseEntity<Encounter> update(@RequestBody EncounterDTO encounterDTO) throws URISyntaxException {
        Encounter encounter = this.encounterService.updateEncounter(encounterDTO);
        return ResponseEntity.created(new URI("/api/encounter/" + encounter.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(encounter.getId()))).body(encounter);
    }
}
