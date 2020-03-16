package org.lamisplus.base.application.repository;

import org.lamisplus.base.application.domiain.model.Person;
import org.lamisplus.base.application.domiain.model.PersonContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonContactRepository extends JpaRepository<PersonContact, Long> {
    PersonContact findBypersonByPersonId(Person person);
    Optional<PersonContact> findByPersonId(Long personId);
}
