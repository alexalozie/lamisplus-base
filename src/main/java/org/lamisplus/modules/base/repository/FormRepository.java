package org.lamisplus.modules.base.repository;

import org.lamisplus.modules.base.domain.entities.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    Optional<Form> findByName(String name);
    List<Form> findAll();

    List<Form> findByServiceName(String service);
    Optional<Form> findByIdAndServiceName(Long Formid, String serviceCode);
}

