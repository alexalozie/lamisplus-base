package org.lamisplus.base.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "person", schema = "public", catalog = "lamisplus")
public class Person {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "dob")
    private LocalDate dob;

    @Basic
    @Column(name = "dob_estimated")
    private Boolean dobEstimated;

    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @Column(name = "last_name")
    private String lastName;

    @Basic
    @Column(name = "other_names")
    private String otherNames;

    @Basic
    @Column(name = "education_id")
    private Long educationId = 1L;

    @Basic
    @Column(name = "gender_id")
    private Long genderId = 1L;

    @Basic
    @Column(name = "occupation_id")
    private Long occupationId = 1L;

    @Basic
    @Column(name = "marital_status_id")
    private Long maritalStatusId = 1L;

    @Basic
    @Column(name = "person_title_id")
    private Long personTitleId = 1L;




/*    @OneToMany(mappedBy = "personByPersonId")
    private Collection<PersonContact> personContactsById;

    @OneToMany(mappedBy = "personByPersonId")
    private Collection<PersonRelative> personRelativesById;*/

    /*
    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Patient> getPatientsById() {
        return patientsById;
    }


    public void setPatientsById(Collection<Patient> patientsById) {
        this.patientsById = patientsById;
    }


     */

    /*
    @OneToMany(mappedBy = "personByPersonId")
    public Collection<PersonContact> getPersonContactsById() {
        return personContactsById;
    }

    public void setPersonContactsById(Collection<PersonContact> personContactsById) {
        this.personContactsById = personContactsById;
    }

     */

    /*
    @OneToMany(mappedBy = "personByPersonId")
    public Collection<PersonRelative> getPersonRelativesById() {
        return personRelativesById;
    }

    public void setPersonRelativesById(Collection<PersonRelative> personRelativesById) {
        this.personRelativesById = personRelativesById;
    }

     */

    /*
    @OneToMany(mappedBy = "personByPersonId")
    public Collection<User> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<User> usersById) {
        this.usersById = usersById;
    }

     */
}
