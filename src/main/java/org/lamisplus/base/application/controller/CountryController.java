package org.lamisplus.base.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.base.application.domiain.model.Country;
import org.lamisplus.base.application.domiain.dto.BadRequestAlertException;
import org.lamisplus.base.application.domiain.dto.HeaderUtil;
import org.lamisplus.base.application.repository.CountryRepository;
import org.lamisplus.base.application.service.CountryService;
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

    private static final String ENTITY_NAME = "country";
    private final CountryRepository countryRepository;
    private final CountryService countryServices;

    private static Object exist(Country o) {
        throw new BadRequestAlertException("Country Already Exist", ENTITY_NAME, "id Already Exist");
    }

    private static Country notExit() {
        throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id is null");
    }

    @PostMapping
    public ResponseEntity<Country> save(@RequestBody Country country) throws URISyntaxException {
        Optional<Country> patient1 = countryRepository.findById(country.getId());
        patient1.map(CountryController::exist);
        Country result = countryServices.save(country);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId()))).body(result);
    }

    @PutMapping
    public ResponseEntity<Country> update(@RequestBody Country country) throws URISyntaxException {
        Optional<Country> country1 = countryRepository.findById(country.getId());
        country1.orElseGet(CountryController::notExit);
        Country result = countryServices.update(country);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId())))
                .body(result);
    }


    @GetMapping
    //@CrossOrigin(origins = "http://10.167.3.126:3000", methods=GET)
    public ResponseEntity<List<Country>> getAllCountry() {
        //log.info("Tested now");
        return ResponseEntity.ok(countryRepository.findAll());

    }


    @GetMapping("/{id}")
    public ResponseEntity<Country> getSingle(@PathVariable Long id) {
        Optional<Country> country = this.countryRepository.findById(id);
        if (country.isPresent()){
            Country countryArchive = country.get();
            return ResponseEntity.ok(countryArchive);
        } else throw new BadRequestAlertException("Record not found with id ", ENTITY_NAME, "id is  Null");

    }


}
