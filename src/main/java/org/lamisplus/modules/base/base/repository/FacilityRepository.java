package org.lamisplus.modules.base.base.repository;

import org.lamisplus.modules.base.base.domiain.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

}
