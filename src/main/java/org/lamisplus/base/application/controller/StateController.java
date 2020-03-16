package org.lamisplus.base.application.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.base.application.domiain.dto.BadRequestAlertException;
import org.lamisplus.base.application.domiain.model.Country;
import org.lamisplus.base.application.domiain.model.State;
import org.lamisplus.base.application.repository.CountryRepository;
import org.lamisplus.base.application.repository.StateRepository;
import org.lamisplus.base.application.service.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/state")
@Slf4j
@RequiredArgsConstructor
public class StateController {
    private static final String ENTITY_NAME = "state";
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final StateService stateService;

    private static Object exist(State o) {
        throw new BadRequestAlertException("State Already Exist", ENTITY_NAME, "id Already Exist");
    }

    private static State notExit() {
        throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id is null");
    }


    @GetMapping
    public ResponseEntity<List<State>> getAllState() {
        return ResponseEntity.ok(this.stateRepository.findAll());
    }


    @GetMapping("/country/{id}")
    public ResponseEntity<List<State>> getAllCountry(@PathVariable Long id) {
        Optional<Country> country = this.countryRepository.findById(id);
        if (country.isPresent()){
            Country country1 = country.get();
            List<State> stateSet = this.stateRepository.findByCountryId(country1);
            return ResponseEntity.ok(stateSet);
        } else throw new BadRequestAlertException("Record not found ", ENTITY_NAME, "id is  Null");

    }


}
