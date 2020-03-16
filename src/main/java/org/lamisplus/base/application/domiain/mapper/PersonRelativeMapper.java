package org.lamisplus.base.application.domiain.mapper;


import org.lamisplus.base.application.domiain.dto.PersonRelativeDTO;
import org.lamisplus.base.application.domiain.model.PersonRelative;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonRelativeMapper {
    PersonRelative toPersonRelative(PersonRelativeDTO personRelativeDTO);

    PersonRelativeDTO toPersonRelativeDTO(PersonRelative personRelative);
}
