package org.lamisplus.base.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.base.application.domiain.dto.BadRequestAlertException;
import org.lamisplus.base.application.domiain.dto.HeaderUtil;
import org.lamisplus.base.application.domiain.dto.VisitDTO;
import org.lamisplus.base.application.domiain.mapper.VisitMapper;
import org.lamisplus.base.application.domiain.model.Visit;
import org.lamisplus.base.application.repository.VisitRepository;
import org.lamisplus.base.application.service.VisitService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@RestController
@RequestMapping("api/visits")
@Slf4j
@RequiredArgsConstructor
public class VisitController {

    private static final String ENTITY_NAME = "Visit";
    private final VisitRepository visitRepository;
    private final VisitService visitService;
    private final VisitMapper visitMapper;

    private static Visit notExit() {
        throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id is null");
    }


    @GetMapping
    public Iterable findAll() {
        return visitRepository.findAll();
    }

    @GetMapping("/datevisit")
    public Iterable findPatientByDateStart() {
        return this.visitService.getVisitByDateStart();
    }

    @GetMapping("/{id}")
    public Visit findOne(@PathVariable Long id ) {
        return visitRepository.findById(id)
                .orElseThrow(RecordNotFoundException::new);
    }

    @GetMapping("/patient/{PatientId}")
    public Iterable findOnePatientId(@PathVariable Long PatientId) throws NullPointerException {
        // WORK ON IT
        return visitRepository.findByPatientId(PatientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Visit> save(@RequestBody VisitDTO visitDTO) throws URISyntaxException {
        Visit visit = this.visitService.save(visitDTO);
        return ResponseEntity.created(new URI("/api/visits/" + visit.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(visit.getId()))).body(visit);
    }

    @PutMapping("/{id}")
    public Visit update(@RequestBody Visit visit, @PathVariable Long id) {
        if (visit.getId() != (id)) {
            throw new RecordIdMismatchException(id);
        }
        visitRepository.findById(id)
                .orElseThrow(RecordNotFoundException::new);
        return visitRepository.save(visit);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        visitRepository.findById(id)
                .orElseThrow(RecordNotFoundException::new);
        visitRepository.deleteById(id);
    }


}
