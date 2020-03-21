package org.lamisplus.modules.base.base.repository;


import org.lamisplus.modules.base.base.domiain.model.IdentifierType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentifierTypeRepository extends JpaRepository<IdentifierType, Long> {
    IdentifierType findByIdentifierTypeName(String name);
}
