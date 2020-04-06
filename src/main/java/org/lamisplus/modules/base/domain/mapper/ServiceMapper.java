package org.lamisplus.modules.base.domain.mapper;

import org.lamisplus.modules.base.domain.dto.ServiceDTO;
import org.lamisplus.modules.base.domain.entities.Program;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDTO toService(Program program);
    Program toServiceDTO(ServiceDTO serviceDTO);
}
