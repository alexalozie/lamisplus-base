package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.ServiceDTO;
import org.lamisplus.modules.base.domain.entity.Module;
import org.lamisplus.modules.base.domain.entity.Program;
import org.lamisplus.modules.base.domain.mapper.ServiceMapper;
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
    private final ServiceMapper serviceMapper;
    private final ModuleRepository moduleRepository;

    public Program save(ServiceDTO serviceDTO) {
        Optional<Module> moduleOptional = this.moduleRepository.findById(serviceDTO.getModuleId());
        if(!moduleOptional.isPresent()) throw new EntityNotFoundException(Module.class, "Module Id", serviceDTO.getModuleId() + "");

        Optional<Program> serviceOptional = this.programRepository.findByCode(serviceDTO.getProgramCode());
        if(serviceOptional.isPresent()) throw new RecordExistException(Program.class, "Program Name", serviceDTO.getProgramCode() +"");

        final Program program = this.serviceMapper.toServiceDTO(serviceDTO);

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
