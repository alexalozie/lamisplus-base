package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.CountryController;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entities.Country;
import org.lamisplus.modules.base.domain.entities.State;
import org.lamisplus.modules.base.repository.CountriesRepository;
import org.lamisplus.modules.base.repository.StateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CountryServices {
    private final CountriesRepository countryRepository;
    private final StateRepository stateRepository;
    //private final Country country;

    public Country save(Country country) {
        Optional<Country> country1 = countryRepository.findById(country.getId());
        if(country1.isPresent())throw new RecordExistException(Country.class, "Id", country.getId() +"");
        return countryRepository.save(country);
    }

    public Country update(Long id, Country country) {
        Optional<Country> country1 = countryRepository.findById(id);
        if(!country1.isPresent())throw new EntityNotFoundException(Country.class, "Id", id +"");
        country.setId(id);
        return countryRepository.save(country);
    }
    public Country getCountry(Long id){
        Optional<Country> country = this.countryRepository.findById(id);
        if (!country.isPresent())throw new EntityNotFoundException(Country.class, "Id", id +"");
        return country.get();
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public List<State> getStatesByCountryId(Long id){
        List<State> stateList = this.stateRepository.findAllByCountryId(id);
        return stateList;
    }

}
