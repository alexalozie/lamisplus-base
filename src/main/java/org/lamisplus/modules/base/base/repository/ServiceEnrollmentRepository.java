package org.lamisplus.modules.base.base.repository;


import org.lamisplus.modules.base.base.domiain.model.Patient;
import org.lamisplus.modules.base.base.domiain.model.ServiceEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceEnrollmentRepository extends JpaRepository<ServiceEnrollment, Long> {

  ServiceEnrollment findBypatientByPatientId(Patient patient);

  List<ServiceEnrollment> findByIdentifierValueContainingIgnoreCase(String identifyValue);
}
