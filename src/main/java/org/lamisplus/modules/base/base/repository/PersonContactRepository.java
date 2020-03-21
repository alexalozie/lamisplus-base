package org.lamisplus.modules.base.base.repository;

import org.lamisplus.modules.base.base.domiain.model.Person;
import org.lamisplus.modules.base.base.domiain.model.PersonContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonContactRepository extends JpaRepository<PersonContact, Long> {
    PersonContact findBypersonByPersonId(Person person);
    Optional<PersonContact> findByPersonId(Long personId);
}
