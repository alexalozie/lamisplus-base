package org.lamisplus.base.application.repository;


import org.lamisplus.base.application.domiain.model.IdentifierType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentifierTypeRepository extends JpaRepository<IdentifierType, Long> {
    IdentifierType findByIdentifierTypeName(String name);
}
