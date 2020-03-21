package org.lamisplus.modules.base.controller;


import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.domain.entities.Country;
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

    private static Object exist(State o) {
        throw new EntityNotFoundException(State.class, "Id", String.valueOf(o.getId()));
    }

    private static State notExit() {
        throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id is null");
    }


    @PostMapping
    public ResponseEntity<State> save(@RequestBody State state) throws URISyntaxException {
        stateRepository.findById(state.getId()).map(StateController::exist);
        State result = stateService.save(state);
        return ResponseEntity.created(new URI("/api/state/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId()))).body(result);
    }

    @PutMapping
    public ResponseEntity<State> update(@RequestBody State state) throws URISyntaxException {
        Optional<State> country1 = this.stateRepository.findById(state.getId());
        country1.orElseGet(StateController::notExit);
        State result = stateService.update(state);
        return ResponseEntity.created(new URI("/api/state/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId())))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<State>> getAllState() {
        return ResponseEntity.ok(this.stateRepository.findAll());
    }


    @GetMapping("/country/{stateId}")
    public ResponseEntity<List<State>> getAllCountry(@PathVariable Long stateId) {
        Optional<Country> country = this.countryRepository.findById(stateId);
        if (country.isPresent()){
            Country country1 = country.get();
            List<State> stateSet = this.stateRepository.findBycountryByCountryId(country1);
            return ResponseEntity.ok(stateSet);
        } else throw new EntityNotFoundException(State.class, "Id", stateId +"");
    }
}
