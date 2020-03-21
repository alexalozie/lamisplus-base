package org.lamisplus.modules.base.base.repository;

import org.lamisplus.modules.base.base.domiain.model.PersonRelative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRelativeRepository extends JpaRepository<PersonRelative, Long> {
    List<PersonRelative> findByPersonId(Long personId);

}
