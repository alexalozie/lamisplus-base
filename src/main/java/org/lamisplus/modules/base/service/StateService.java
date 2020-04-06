package org.lamisplus.modules.base.service;


import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.domain.entities.Country;
import org.lamisplus.modules.base.domain.entities.Province;
import org.lamisplus.modules.base.domain.entities.State;
import org.lamisplus.modules.base.domain.entities.Visit;
import org.lamisplus.modules.base.repository.ProvinceRepository;
import org.lamisplus.modules.base.repository.StateRepository;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.repository.CountriesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;
    private final CountriesRepository countryRepository;
    private final ProvinceRepository provinceRepository;

    public State save(Long countryId, State state) {
        Optional<Country> country = this.countryRepository.findById(countryId);
        if (!country.isPresent()) throw new EntityNotFoundException(Country.class, "Country Id", countryId + "");
        return this.stateRepository.save(state);
    }
    public State getState(Long stateId) {
        Optional<State> state = this.stateRepository.findById(stateId);
        if (!state.isPresent()) throw new EntityNotFoundException(State.class, "State Id", stateId + "");
        return state.get();
    }


    public State update(Long id, State state) {
        Optional<State> stateOptional = stateRepository.findById(id);
        if(!stateOptional.isPresent())throw new EntityNotFoundException(State.class, "Id", id +"");
        state.setId(id);
        return stateRepository.save(state);
    }

    public List<Province> getProvincesByStateId(Long stateId) {
        List<Province> provinceList = this.provinceRepository.findAllByStateId(stateId);
        return provinceList;
    }

    public Boolean delete(Long id, State state) {
        return true;
    }

}
