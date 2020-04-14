package org.lamisplus.modules.base.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.entity.Encounter;
import org.lamisplus.modules.base.service.EncounterService;
import org.lamisplus.modules.base.domain.dto.EncounterDTO;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
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
        Encounter encounter = encounterService.save(encounterDTO);
        return ResponseEntity.created(new URI("/api/encounters/" + encounter.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(encounter.getId()))).body(encounter);
    }

    //GET ALL ENCOUNTER
    @ApiOperation(value="getAllEncounters", notes = "response is List of EncounterDTO\n\n" +
            "Example - /api/encounters")
    @GetMapping
    public ResponseEntity<List<EncounterDTO>> getAllEncounters() {
        return ResponseEntity.ok(this.encounterService.getAllEncounters());
    }

    //GETTING LATEST ENCOUNTER(DRUG ORDER, VITALS, LAB TEST, CONSULTATION all patients)
    @ApiOperation(value="getEncounterByFormCodeAndDateEncounter", notes = " formCode=required, dateStart=optional, dateEnd=optional\n\n" +
            "Example - api/encounters/{formCode}?dateStart=01-01-2020&dateEnd=01-04-2020")
    @GetMapping("/{formCode}/{dateStart}/{dateEnd}")
    public ResponseEntity<List<EncounterDTO>> getEncounterByFormCodeAndDate(@PathVariable String formCode,
                                                                 @ApiParam(defaultValue = "",required = false) @PathVariable(required = false) Optional<String> dateStart,
                                                                            @ApiParam(value = "", required = false) @PathVariable(required = false) Optional<String> dateEnd) throws URISyntaxException {
        log.info("Date is "+ dateStart);
        List<EncounterDTO> encounterDTOS = this.encounterService.getEncounterByFormCodeAndDate(formCode, dateStart, dateEnd);
        return ResponseEntity.ok(encounterDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncounterDTO> getEncounter(@PathVariable Long id) {
        return ResponseEntity.ok(this.encounterService.getEncounter(id));
    }

    @GetMapping("/{id}/form-data")
    public ResponseEntity<List> getFormDataByEncounterId(@PathVariable Long id) {
        return ResponseEntity.ok(this.encounterService.getFormDataByEncounterId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Encounter> update(@PathVariable Long id, @RequestBody EncounterDTO encounterDTO) throws URISyntaxException {
        Encounter encounter1 = this.encounterService.update(id, encounterDTO);
        return ResponseEntity.created(new URI("/api/encounters/" + id))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(id))).body(encounter1);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id, @RequestBody Encounter encounter) {
        return this.encounterService.delete(id, encounter);
    }
}
