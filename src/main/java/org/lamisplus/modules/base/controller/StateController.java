package org.lamisplus.modules.base.controller;


import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.domain.entities.Country;
import org.lamisplus.modules.base.domain.entities.Province;
import org.lamisplus.modules.base.domain.entities.State;
import org.lamisplus.modules.base.repository.CountriesRepository;
import org.lamisplus.modules.base.repository.StateRepository;
import org.lamisplus.modules.base.service.StateService;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/state")
@RequiredArgsConstructor
public class StateController {
    private static final String ENTITY_NAME = "state";
    private final StateRepository stateRepository;
    private final CountriesRepository countryRepository;
    private final StateService stateService;


    @PostMapping
    public ResponseEntity<State> save(@RequestParam Long countryId, @RequestBody State state) throws URISyntaxException {

        State result = stateService.save(countryId, state);
        return ResponseEntity.created(new URI("/api/state/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId()))).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> update(@PathVariable Long id, @RequestBody State state) throws URISyntaxException {
        State result = stateService.update(id, state);
        return ResponseEntity.created(new URI("/api/state/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId())))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<State>> getAllStates() {
        return ResponseEntity.ok(this.stateRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> getState(@PathVariable Long id) {
        return ResponseEntity.ok(stateService.getState(id));
    }

    @GetMapping("/{id}/provinces")
    public ResponseEntity<List<Province>> getProvincesByStateId(@PathVariable Long id) {
        return ResponseEntity.ok(stateService.getProvincesByStateId(id));
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id, @RequestBody State state) throws RecordNotFoundException {
        return this.stateService.delete(id, state);
    }
}
