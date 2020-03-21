package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.ModuleDTO;
import org.lamisplus.modules.base.domain.dto.ServiceDTO;
import org.lamisplus.modules.base.domain.entities.Module;
import org.lamisplus.modules.base.domain.entities.Service;
import org.lamisplus.modules.base.domain.mapper.ServiceMapper;
import org.lamisplus.modules.base.repository.ModuleRepository;
import org.lamisplus.modules.base.repository.ServicesRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProgramService {
    private final ServicesRepository servicesRepository;
    private final ServiceMapper serviceMapper;
    private final ModuleRepository moduleRepository;

    public Service save(ServiceDTO serviceDTO) {
        Optional<Module> moduleOptional = this.moduleRepository.findById(serviceDTO.getModuleId());
        if(!moduleOptional.isPresent()) throw new EntityNotFoundException(Module.class, "Module Id", serviceDTO.getModuleId() + "");

        Optional<Service> serviceOptional = this.servicesRepository.findByServiceName(serviceDTO.getServiceName());
        if(serviceOptional.isPresent()) throw new RecordExistException(Service.class, "Service Name", serviceDTO.getServiceName() +"");

        final Service service = this.serviceMapper.toServiceDTO(serviceDTO);

        return this.servicesRepository.save(service);
    }

    public List<Service> allServiceByModuleId(Long moduleId){
        List<Service> serviceList = this.servicesRepository.findByModuleId(moduleId);
        if(serviceList.size() > 0 || serviceList == null) throw new EntityNotFoundException(Module.class, "Module Id", moduleId + "");

        return serviceList;

    }



}
