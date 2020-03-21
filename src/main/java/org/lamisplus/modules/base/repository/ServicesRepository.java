package org.lamisplus.modules.base.repository;

import org.lamisplus.modules.base.domain.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicesRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByServiceName(String name);

    List<Service> findByModuleId(Long moduleId);
}

