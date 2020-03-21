package org.lamisplus.modules.base.base.service;

import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.base.domiain.model.Country;
import org.lamisplus.modules.base.base.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional
@Slf4j
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    public Country update(@RequestBody Country country) {
        Country result = countryRepository.save(country);
        return countryRepository.save(result);
    }
}
