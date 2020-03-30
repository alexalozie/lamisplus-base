package org.lamisplus.modules.base.repository;


import org.lamisplus.modules.base.domain.entities.ApplicationCodeset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationCodesetRepository extends JpaRepository<ApplicationCodeset, Long> {

    List<ApplicationCodeset> findBycodesetGroup(String codeset_group);

    Optional<ApplicationCodeset> findByDisplay(String display);

    Optional<ApplicationCodeset> findByDisplayAndCodesetGroup(String display, String codeSetGroup);

    List<ApplicationCodeset> findAllByCodesetGroup(String codeSetGroup);
}
