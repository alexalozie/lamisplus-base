package org.lamisplus.modules.base.base.domiain.mapper;


import org.lamisplus.base.module.domiain.dto.EncounterDTO;
import org.lamisplus.modules.base.base.domiain.model.Encounter;
import org.lamisplus.modules.base.base.domiain.model.Patient;
import org.lamisplus.modules.base.base.domiain.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EncounterMapper {
    Encounter toEncounter(EncounterDTO encounterDTO);
    EncounterDTO toEncounterDTO(Encounter encounter);

    @Mapping(source="person.id", target="personId")
    @Mapping(source="patient.id", target="patientId")
    @Mapping(source="encounter.id", target="encounterId")
    EncounterDTO toEncounterDTO(Person person, Patient patient, Encounter encounter);
}
