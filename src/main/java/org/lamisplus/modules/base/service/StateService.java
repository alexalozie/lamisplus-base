package org.lamisplus.modules.base.service;


import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.domain.entities.Country;
import org.lamisplus.modules.base.domain.entities.State;
import org.lamisplus.modules.base.repository.StateRepository;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.repository.CountriesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;
    private final CountriesRepository countryRepository;

    public State save(@RequestBody State state) {
        Optional<Country> country = this.countryRepository.findById(state.getCountryByCountryId().getId());
        if (country.isPresent()) {
            Country country1 = country.get();
            state.setCountryByCountryId(country1);
            return this.stateRepository.save(state);
        } else throw new EntityNotFoundException(Country.class, "Country Id", "id is null");

    }


    public State update(@RequestBody State state) {
/*        ModelMapper modelMapper = new ModelMapper();
        State state1 = modelMapper.map(state, State.class);
        Country country = this.countryRepository.getOne(state.getCountryByCountryId().getId());
        state1.setCountryByCountryId(country);*/
        return this.stateRepository.save(state);
    }

}
