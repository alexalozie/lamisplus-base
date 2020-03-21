package org.lamisplus.modules.base.repository;

import org.lamisplus.modules.base.domain.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
List<Person> findAllByOrderByIdDesc();
Optional<Person> findById(Long personId);
}
