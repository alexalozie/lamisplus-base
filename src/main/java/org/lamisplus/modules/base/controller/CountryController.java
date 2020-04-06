package org.lamisplus.modules.base.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.entities.Country;
import org.lamisplus.modules.base.domain.entities.State;
import org.lamisplus.modules.base.repository.CountriesRepository;
import org.lamisplus.modules.base.service.CountryServices;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/countries")
@Slf4j
@RequiredArgsConstructor
public class CountryController {

    private final CountriesRepository countryRepository;
    private final CountryServices countryServices;
    private static final String ENTITY_NAME = "country";

    @PostMapping
    public ResponseEntity<Country> save(@RequestBody Country country) throws URISyntaxException {
        Country result = countryServices.save(country);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId()))).body(result);
    }

    @PutMapping("{id}")
    public ResponseEntity<Country> update(@PathVariable Long id, @RequestBody Country country) throws URISyntaxException {
        Country result = countryServices.update(id, country);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId())))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryServices.getAllCountries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable Long id) {
        return ResponseEntity.ok(countryServices.getCountry(id));
    }

    @GetMapping("/{id}/states")
    public ResponseEntity<List<State>> getStatesByCountryId(@PathVariable Long id) {
        return ResponseEntity.ok(countryServices.getStatesByCountryId(id));
    }

}
