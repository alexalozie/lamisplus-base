package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.ProgramDTO;
import org.lamisplus.modules.base.domain.entity.Module;
import org.lamisplus.modules.base.domain.entity.Program;
import org.lamisplus.modules.base.domain.mapper.ProgramMapper;
import org.lamisplus.modules.base.repository.ModuleRepository;
import org.lamisplus.modules.base.repository.ProgramRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
    private final ModuleRepository moduleRepository;

    public Program save(ProgramDTO programDTO) {
        Optional<Module> moduleOptional = this.moduleRepository.findById(programDTO.getModuleId());
        if(!moduleOptional.isPresent()) throw new EntityNotFoundException(Module.class, "Module Id", programDTO.getModuleId() + "");

        Optional<Program> serviceOptional = this.programRepository.findByCode(programDTO.getProgramCode());
        if(serviceOptional.isPresent()) throw new RecordExistException(Program.class, "Program Name", programDTO.getProgramCode() +"");

        final Program program = this.programMapper.toProgramDTO(programDTO);

        return this.programRepository.save(program);
    }

    public List<Program> getServiceByModuleId(Long moduleId){
        List<Program> programList = this.programRepository.findByModuleId(moduleId);
        if(programList.size() > 0 || programList == null) throw new EntityNotFoundException(Module.class, "Module Id", moduleId + "");

        return programList;

    }

    public List<Program> getAllPrograms(){
        List<Program> programList = this.programRepository.findAll();
        return programList;
    }



}
