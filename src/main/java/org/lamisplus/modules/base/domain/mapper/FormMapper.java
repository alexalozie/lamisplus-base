package org.lamisplus.modules.base.domain.mapper;

import org.lamisplus.modules.base.domain.dto.FormDTO;
import org.lamisplus.modules.base.domain.entities.Form;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FormMapper {
    FormDTO toForm(Form form);
    Form toFormDTO(FormDTO formDTO);
}
