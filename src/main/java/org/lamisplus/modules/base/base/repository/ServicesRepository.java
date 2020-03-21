package org.lamisplus.modules.base.base.repository;

import org.lamisplus.modules.base.base.domiain.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<Service, Long> {
    Service findByServiceName(String name);
}

