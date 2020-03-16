package org.lamisplus.base.application.domiain.mapper;


import org.lamisplus.base.application.domiain.dto.VisitDTO;
import org.lamisplus.base.application.domiain.model.Visit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    Visit toVisit(VisitDTO visitDTO);
}
