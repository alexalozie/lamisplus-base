package org.lamisplus.modules.base.repository;

import org.lamisplus.modules.base.domain.entity.Person;
import org.lamisplus.modules.base.domain.entity.PersonContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonContactRepository extends JpaRepository<PersonContact, Long> {
    PersonContact findByPersonByPersonId(Person person);
    PersonContact findAllByPersonId(Long id);
    Optional<PersonContact> findByPersonId(Long personId);
}
