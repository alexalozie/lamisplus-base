package org.lamisplus.modules.base.domain.mapper;


import org.lamisplus.modules.base.domain.dto.EncounterDTO;
import org.lamisplus.modules.base.domain.dto.EncounterFormDataDTO;
import org.lamisplus.modules.base.domain.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EncounterMapper {
    Encounter toEncounter(EncounterDTO encounterDTO);
    EncounterDTO toEncounterDTO(Encounter encounter);
    FormData toFormData(EncounterDTO encounterDTO);


    @Mappings({
            @Mapping(source="person.id", target="personId"),
            @Mapping(source="patient.id", target="patientId"),
            @Mapping(source="encounter.id", target="encounterId"),
            @Mapping(source="program.id", target="programId"),
    })
    EncounterDTO toEncounterDTO(Person person, Patient patient, Encounter encounter, Program program);

    @Mappings({
            @Mapping(source="person.id", target="personId"),
            @Mapping(source="patient.id", target="patientId")
    })
    EncounterFormDataDTO toEncounterFormDataDTO(Person person, Patient patient);
}
