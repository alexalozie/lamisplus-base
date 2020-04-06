package org.lamisplus.modules.base.repository;

import org.lamisplus.modules.base.domain.entities.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    //Optional<Program> findByProgramCode(String name);

    List<Program> findByModuleId(Long moduleId);

    Optional<Program> findByProgramCode(String programCode);
}

