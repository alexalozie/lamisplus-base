package org.lamisplus.base.application.repository;

import org.lamisplus.base.application.domiain.model.Province;
import org.lamisplus.base.application.domiain.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    List<Province> findBystateByStateId(State state);

    Optional<Province> findById(Long id);
}
