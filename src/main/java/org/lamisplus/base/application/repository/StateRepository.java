package org.lamisplus.base.application.repository;

import org.lamisplus.base.application.domiain.model.Country;
import org.lamisplus.base.application.domiain.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    List<State> findByCountryId(Country country1);

    Optional<State> findById(Long id);
}
