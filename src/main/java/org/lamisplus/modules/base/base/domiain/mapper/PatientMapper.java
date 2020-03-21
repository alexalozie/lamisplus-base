package org.lamisplus.modules.base.base.domiain.mapper;


import org.lamisplus.base.module.domiain.dto.PatientDTO;
import org.lamisplus.modules.base.base.domiain.model.Patient;
import org.lamisplus.modules.base.base.domiain.model.Person;
import org.lamisplus.modules.base.base.domiain.model.PersonContact;
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
