package org.lamisplus.base.application.repository;


import org.lamisplus.base.application.domiain.model.ApplicationCodeset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationCodesetRepository extends JpaRepository<ApplicationCodeset, Long> {

    List<ApplicationCodeset> findBycodesetGroup(String codeset_group);
}
