package org.lamisplus.modules.base.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.domain.dto.ModuleDTO;
import org.lamisplus.modules.base.domain.entity.Module;
import org.lamisplus.modules.base.domain.mapper.ModuleMapper;
import org.lamisplus.modules.base.repository.ModuleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;

    public Module save(ModuleDTO moduleDTO) {
        Optional<Module> moduleOptional = this.moduleRepository.findByName(moduleDTO.getName());
        if(!moduleOptional.isPresent()) throw new EntityNotFoundException(Module.class, "Module Id", moduleDTO.getName());

        final Module module = this.moduleMapper.toModuleDTO(moduleDTO);

        return this.moduleRepository.save(module);
    }

    public List<Module> getAllModules(){
        List<Module> moduleList = this.moduleRepository.findAll();
        //if(moduleList.size() > 0 || moduleList == null) throw new EntityNotFoundException(Module.class, "Module", moduleId + "");
        return moduleList;

    }
}
