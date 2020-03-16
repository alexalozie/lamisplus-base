package org.lamisplus.base.application.repository;

import org.lamisplus.base.application.domiain.model.PersonRelative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRelativeRepository extends JpaRepository<PersonRelative, Long> {
    List<PersonRelative> findByPersonId(Long personId);

}
