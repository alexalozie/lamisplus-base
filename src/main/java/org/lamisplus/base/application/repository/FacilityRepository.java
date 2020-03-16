package org.lamisplus.base.application.repository;

import org.lamisplus.base.application.domiain.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

}
