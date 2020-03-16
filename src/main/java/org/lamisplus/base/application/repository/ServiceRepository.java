package org.lamisplus.base.application.repository;

import org.lamisplus.base.application.domiain.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service findByServiceName(String name);
}

