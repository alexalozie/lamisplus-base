package org.lamisplus.modules.base.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.entities.Country;
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

    private static Object exist(Country o) {
        throw new BadRequestAlertException("Country Already Exist", ENTITY_NAME, "id Already Exist");
    }

    private static Country notExit() {
        throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id is null");
    }

    @PostMapping
    public ResponseEntity<Country> saveCountry(@RequestBody Country country) throws URISyntaxException {
        Optional<Country> patient1 = countryRepository.findById(country.getId());
        patient1.map(CountryController::exist);
        Country result = countryServices.save(country);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId()))).body(result);
    }

    @PutMapping
    public ResponseEntity<Country> updateCountry(@RequestBody Country country) throws URISyntaxException {
        Optional<Country> country1 = countryRepository.findById(country.getId());
        country1.orElseGet(CountryController::notExit);
        Country result = countryServices.update(country);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId())))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountry() {
        log.info("Tested now");
        return ResponseEntity.ok(countryRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getOneCountry(@PathVariable Long id) {
        Optional<Country> country = this.countryRepository.findById(id);
        if (country.isPresent()){
            Country countryArchive = country.get();
            return ResponseEntity.ok(countryArchive);
        } else throw new BadRequestAlertException("Record not found with id ", ENTITY_NAME, "id is  Null");
    }
}
