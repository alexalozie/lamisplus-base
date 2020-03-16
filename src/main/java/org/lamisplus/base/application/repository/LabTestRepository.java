package org.lamisplus.base.application.repository;

import org.lamisplus.base.application.domiain.model.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long> {
    List<LabTest> findAllByLabTestCategoryId(Long labTestCategoryId);
}
