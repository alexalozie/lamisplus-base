package org.lamisplus.base.application.domiain.mapper;


import org.lamisplus.base.application.domiain.dto.PatientDTO;
import org.lamisplus.base.application.domiain.model.Patient;
import org.lamisplus.base.application.domiain.model.Person;
import org.lamisplus.base.application.domiain.model.PersonContact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    Person toPerson(PatientDTO patientDTO);

    PersonContact toPersonContact(PatientDTO patientDTO);

    Patient toPatient(PatientDTO patientDTO);

    @Mapping(source="person.id", target="personId")
    //@Mapping(source="personContact.id", target="personContactId")
    @Mapping(source="patient.id", target="patientId")
    PatientDTO toPatientDTO(Person person, PersonContact personContact, Patient patient);
}
