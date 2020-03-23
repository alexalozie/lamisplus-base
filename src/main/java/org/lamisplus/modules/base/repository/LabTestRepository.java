package org.lamisplus.modules.base.repository;

import org.lamisplus.modules.base.domain.entities.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long> {
    List<LabTest> findAllByLabTestCategoryId(Long labTestGroupId);
}