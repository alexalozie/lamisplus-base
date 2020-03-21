package org.lamisplus.modules.base.base.repository;

import org.lamisplus.modules.base.base.domiain.model.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long> {
    List<LabTest> findAllByLabTestCategoryId(Long labTestCategoryId);
}
