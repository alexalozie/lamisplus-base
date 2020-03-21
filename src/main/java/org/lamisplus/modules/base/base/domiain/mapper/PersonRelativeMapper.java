package org.lamisplus.modules.base.base.domiain.mapper;


import org.lamisplus.modules.base.base.domiain.dto.PersonRelativeDTO;
import org.lamisplus.modules.base.base.domiain.model.PersonRelative;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonRelativeMapper {
    PersonRelative toPersonRelative(PersonRelativeDTO personRelativeDTO);

    PersonRelativeDTO toPersonRelativeDTO(PersonRelative personRelative);
}
