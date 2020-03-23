package org.lamisplus.modules.base.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.VisitDTO;
import org.lamisplus.modules.base.domain.entities.Visit;
import org.lamisplus.modules.base.repository.VisitRepository;
import org.lamisplus.modules.base.service.VisitService;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/visits")
@RequiredArgsConstructor
public class VisitController {
    private static final String ENTITY_NAME = "Visit";
    private final VisitRepository visitRepository;
    private final VisitService visitService;

    @GetMapping
    public Iterable findAll() {
        return visitRepository.findAll();
    }

    @GetMapping("/{id}")
    public Visit getSinglePatient(@PathVariable Long id) {
        return visitRepository.findById(id)
                .orElseThrow(RecordNotFoundException::new);
    }

    @ApiOperation(value="getSortedVisit", notes = "patientId= required, dateStart=optional, dateEnd=optional\n\n" +
            "Example - /api/visits/patient/20/?dateStart=02-03-2020")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<VisitDTO>> getSortedVisit(@PathVariable Optional<Long> patientId, @RequestParam(required = false) Optional<String> dateStart,
                                                         @RequestParam(required = false) Optional <String> dateEnd) {
       /* // WORK ON IT
        if (visitRepository.findByPatientId(patientId).size() < 1)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Patients Not Found", new RecordNotFoundException(patientId.toString()));
        else
            return visitRepository.findByPatientId(patientId);
*/
        return ResponseEntity.ok(visitService.getSortedVisit(patientId,dateStart,dateEnd));
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Visit> saveVisit(@RequestBody VisitDTO visitDTO) throws URISyntaxException {
        Visit visit1 = this.visitService.save(visitDTO);
        return ResponseEntity.created(new URI("/api/visits/" + visit1.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(visit1.getId()))).body(visit1);
    }

    @PutMapping("/{id}")
    public Visit updateVisit(@RequestBody Visit visit, @PathVariable Long id) {
        if (visit.getId() != (id)) {
            throw new RecordIdMismatchException(id);
        }
        visitRepository.findById(id)
                .orElseThrow(RecordNotFoundException::new);
        return visitRepository.save(visit);
    }

    @DeleteMapping("/{id}")
    public void archiveVisit(@PathVariable Long id) {
        visitRepository.findById(id)
                .orElseThrow(RecordNotFoundException::new);
        visitRepository.deleteById(id);
    }
}
