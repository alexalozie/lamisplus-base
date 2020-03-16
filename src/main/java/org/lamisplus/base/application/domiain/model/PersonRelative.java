package org.lamisplus.base.application.domiain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "person_relative", schema = "public", catalog = "lamisplus")
public class PersonRelative {
    private Long id;
    private String address;
    private String alternatePhoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String mobilePhoneNumber;
    private String otherNames;
    private Long relationshipTypeId;
    private Long personId;
    private Person personByPersonId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "alternate_phone_number", nullable = true, length = 255)
    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 255)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 255)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "mobile_phone_number", nullable = true, length = 255)
    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    @Basic
    @Column(name = "other_names", nullable = true, length = 255)
    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    @Basic
    @Column(name = "relationship_type_id", nullable = true)
    public Long getRelationshipTypeId() {
        return relationshipTypeId;
    }

    public void setRelationshipTypeId(Long relationshipTypeId) {
        this.relationshipTypeId = relationshipTypeId;
    }

    @Basic
    @Column(name = "person_id", nullable = true, insertable = false, updatable = false)
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRelative that = (PersonRelative) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(address, that.address) &&
                Objects.equals(alternatePhoneNumber, that.alternatePhoneNumber) &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(mobilePhoneNumber, that.mobilePhoneNumber) &&
                Objects.equals(otherNames, that.otherNames) &&
                Objects.equals(relationshipTypeId, that.relationshipTypeId) &&
                Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, alternatePhoneNumber, email, firstName, lastName, mobilePhoneNumber, otherNames, relationshipTypeId, personId);
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }
}
