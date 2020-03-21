package org.lamisplus.modules.base.domain.mapper;


import org.lamisplus.modules.base.domain.dto.EncounterDTO;
import org.lamisplus.modules.base.domain.entities.Encounter;
import org.lamisplus.modules.base.domain.entities.Patient;
import org.lamisplus.modules.base.domain.entities.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EncounterMapper {
    Encounter toEncounter(EncounterDTO encounterDTO);
    EncounterDTO toEncounterDTO(Encounter encounter);


    @Mappings({
            @Mapping(source="person.id", target="personId"),
            @Mapping(source="patient.id", target="patientId"),
            @Mapping(source="encounter.id", target="encounterId")
    })
    EncounterDTO toEncounterDTO(Person person, Patient patient, Encounter encounter);
}
