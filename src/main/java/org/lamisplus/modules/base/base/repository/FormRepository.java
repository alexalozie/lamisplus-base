package org.lamisplus.modules.base.base.repository;

import org.lamisplus.modules.base.base.domiain.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    Form findByName(String name);
}

