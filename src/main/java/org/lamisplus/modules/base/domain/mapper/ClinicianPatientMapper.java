package org.lamisplus.modules.base.domain.mapper;

import org.lamisplus.modules.base.domain.dto.ClinicianPatientDTO;
import org.lamisplus.modules.base.domain.entities.ClinicianPatient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ClinicianPatientMapper {

    ClinicianPatient toClinicianPatient(ClinicianPatientDTO clinicianPatientDTO);
}
