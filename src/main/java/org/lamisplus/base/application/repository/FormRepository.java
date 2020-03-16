package org.lamisplus.base.application.repository;

import org.lamisplus.base.application.domiain.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    Form findByName(String name);
}

