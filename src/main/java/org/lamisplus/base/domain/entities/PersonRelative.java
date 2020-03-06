package org.lamisplus.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "person_relative", schema = "public", catalog = "lamisplus")
public class PersonRelative {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "alternate_phone_number")
    private String alternatePhoneNumber;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @Column(name = "last_name")
    private String lastName;

    @Basic
    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    @Basic
    @Column(name = "other_names")
    private String otherNames;

    @Basic
    @Column(name = "relationship_type_id")
    private Long relationshipTypeId;

    @Basic
    @Column(name = "person_id", insertable = false, updatable = false)
    private Long personId;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person personByPersonId;

}
