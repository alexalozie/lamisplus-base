package org.lamisplus.base.application.domiain.mapper;


import org.lamisplus.base.application.domiain.dto.EncounterDTO;
import org.lamisplus.base.application.domiain.model.Encounter;
import org.lamisplus.base.application.domiain.model.Patient;
import org.lamisplus.base.application.domiain.model.Person;
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
