package org.lamisplus.base.application.repository;


import org.lamisplus.base.application.domiain.model.Patient;
import org.lamisplus.base.application.domiain.model.ServiceEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceEnrollmentRepository extends JpaRepository<ServiceEnrollment, Long> {

  ServiceEnrollment findBypatientByPatientId(Patient patient);

  List<ServiceEnrollment> findByIdentifierValueContainingIgnoreCase(String identifyValue);
}
