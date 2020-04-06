package org.lamisplus.modules.base.repository;

import org.lamisplus.modules.base.domain.entities.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    Optional<Form> findByCode(String code);
    List<Form> findAll();

    List<Form> findByProgramCode(String programCode);
    Optional<Form> findByIdAndProgramCode(Long Formid, String programCode);
}

