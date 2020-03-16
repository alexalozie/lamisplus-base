package org.lamisplus.base.application.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.base.application.domiain.dto.BadRequestAlertException;
import org.lamisplus.base.application.domiain.model.Country;
import org.lamisplus.base.application.domiain.model.State;
import org.lamisplus.base.application.repository.CountryRepository;
import org.lamisplus.base.application.repository.StateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    public State save(@RequestBody State state) {
        Optional<Country> country = this.countryRepository.findById(state.getCountryByCountryId().getId());
        if (country.isPresent()){
            Country country1 = country.get();
            state.setCountryByCountryId(country1);
            return this.stateRepository.save(state);
        } else throw new BadRequestAlertException("Country id did not exist ", "", "id is null");

    }

}
