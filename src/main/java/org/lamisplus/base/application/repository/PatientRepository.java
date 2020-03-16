package org.lamisplus.base.application.repository;

import org.lamisplus.base.application.domiain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select p from Person p where lower(p.firstName) like lower(concat('%', :search, '%')) " +
            "or lower(p.lastName) like lower(concat('%', :search, '%'))")
    List<Patient> findByFirstNameLastName(String search);

    //List<Patient> findAll();

    Optional<Patient> findByHospitalNumber(String number);

    Optional<Patient> findByPersonId(Long PersonId);

    Optional<Patient> findById(Long id);

    void deleteById(Long id);
}
