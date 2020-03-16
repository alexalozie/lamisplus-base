package org.lamisplus.base.application.repository;


import org.lamisplus.base.application.domiain.model.Codeset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodesetRepository extends JpaRepository<Codeset, Long> {
    List<Codeset> findBycodesetGroup(String codeset_group);
}



