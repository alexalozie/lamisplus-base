package org.lamisplus.modules.base.domain.mapper;

import org.lamisplus.modules.base.domain.dto.ProvinceDTO;
import org.lamisplus.modules.base.domain.entities.Province;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProvinceMapper {
    ProvinceDTO toProvince(Province province);
    Province toProvinceDTO(ProvinceDTO provinceDTODTO);
}
