package org.lamisplus.modules.base.repository;


import org.lamisplus.modules.base.domain.entities.ApplicationCodeset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationCodesetRepository extends JpaRepository<ApplicationCodeset, Long> {

    List<ApplicationCodeset> findBycodesetGroup(String codeset_group);
}
