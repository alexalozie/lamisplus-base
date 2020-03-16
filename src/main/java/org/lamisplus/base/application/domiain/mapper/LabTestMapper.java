package org.lamisplus.base.application.domiain.mapper;


import org.lamisplus.base.application.domiain.dto.LabTestDTO;
import org.lamisplus.base.application.domiain.model.LabTest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabTestMapper {
    LabTestDTO toLabTest(LabTest labTest);
}
