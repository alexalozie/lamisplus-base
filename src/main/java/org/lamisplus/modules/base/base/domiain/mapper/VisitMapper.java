package org.lamisplus.modules.base.base.domiain.mapper;


import org.lamisplus.modules.base.base.domiain.dto.VisitDTO;
import org.lamisplus.modules.base.base.domiain.model.Visit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    Visit toVisit(VisitDTO visitDTO);
}
