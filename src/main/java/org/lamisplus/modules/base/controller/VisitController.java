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
    public ResponseEntity<List<VisitDTO>> getAllVisits() {
        return ResponseEntity.ok(visitService.getAllVisits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDTO> getVisit(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.getVisit(id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Visit> save(@RequestBody VisitDTO visitDTO) throws URISyntaxException {
        Visit visit1 = this.visitService.save(visitDTO);
        return ResponseEntity.created(new URI("/api/visits/" + visit1.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(visit1.getId()))).body(visit1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visit> updateVisit(@RequestBody Visit visit, @PathVariable Long id) throws URISyntaxException{
        Visit result = visitService.update(id, visit);
        return ResponseEntity.created(new URI("/api/state/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId())))
                .body(result);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id, @RequestBody Visit visit) throws RecordNotFoundException {
        return this.visitService.delete(id, visit);
    }
}
