package org.lamisplus.base.application.repository;

import org.lamisplus.base.application.domiain.model.LabTestGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabTestGroupRepository extends JpaRepository<LabTestGroup, Long> {
}
