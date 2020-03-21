package org.lamisplus.modules.base.base.domiain.mapper;


import org.lamisplus.modules.base.base.domiain.dto.LabTestDTO;
import org.lamisplus.modules.base.base.domiain.model.LabTest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabTestMapper {
    LabTestDTO toLabTest(LabTest labTest);
}
