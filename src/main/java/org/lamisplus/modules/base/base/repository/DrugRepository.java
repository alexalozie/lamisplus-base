package org.lamisplus.modules.base.base.repository;

import org.lamisplus.modules.base.base.domiain.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
}
