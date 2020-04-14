package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode
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
    private Long educationId;

    @Basic
    @Column(name = "gender_id")
    private Long genderId;

    @Basic
    @Column(name = "occupation_id")
    private Long occupationId;

    @Basic
    @Column(name = "marital_status_id")
    private Long maritalStatusId;

    @Basic
    @Column(name = "person_title_id")
    private Long personTitleId;

    @Basic
    @Column(name = "uuid")
    private String uuid;

    @Basic
    @Column(name = "date_created")
    @JsonIgnore
    private Timestamp dateCreated;

    @Basic
    @Column(name = "created_by")
    @JsonIgnore
    private String createdBy;

    @Basic
    @Column(name = "date_modified")
    @JsonIgnore
    private Timestamp dateModified;

    @Basic
    @Column(name = "modified_by")
    @JsonIgnore
    private String modifiedBy;

    @Basic
    @Column(name = "archived")
    private Integer archived;




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
