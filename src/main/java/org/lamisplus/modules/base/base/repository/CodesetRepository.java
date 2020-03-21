package org.lamisplus.modules.base.base.repository;


import org.lamisplus.modules.base.base.domiain.model.Codeset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodesetRepository extends JpaRepository<Codeset, Long> {
    List<Codeset> findBycodesetGroup(String codeset_group);
}



