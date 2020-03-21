package org.lamisplus.modules.base.domain.mapper;

import org.lamisplus.modules.base.domain.dto.PatientDTO;
import org.lamisplus.modules.base.domain.entities.Patient;
import org.lamisplus.modules.base.domain.entities.Person;
import org.lamisplus.modules.base.domain.entities.PersonContact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    Person toPerson(PatientDTO patientDTO);

    PersonContact toPersonContact(PatientDTO patientDTO);

    Patient toPatient(PatientDTO patientDTO);


    @Mappings({
            @Mapping(source="person.id", target="personId"),
            //@Mapping(source="personContact.id", target="personContactId")
            @Mapping(source="patient.id", target="patientId")
    })
    PatientDTO toPatientDTO(Person person, PersonContact personContact, Patient patient);
}
