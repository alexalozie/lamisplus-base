package org.lamisplus.modules.base.repository;

import org.lamisplus.modules.base.domain.entities.Country;
import org.lamisplus.modules.base.domain.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    List<State> findBycountryByCountryId(Country country);

}