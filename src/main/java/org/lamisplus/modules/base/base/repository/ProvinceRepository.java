package org.lamisplus.modules.base.base.repository;

import org.lamisplus.modules.base.base.domiain.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    List<Province> findByStateId(Long stateId);

    Optional<Province> findById(Long id);
}
