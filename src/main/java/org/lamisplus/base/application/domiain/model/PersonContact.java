package org.lamisplus.base.application.domiain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "person_contact", schema = "public", catalog = "lamisplus")
public class PersonContact {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "mobile_phone_number", nullable = true, length = -1)
    private String mobilePhoneNumber;

    @Basic
    @Column(name = "alternate__phone_number", nullable = true, length = -1)
    private String alternatePhoneNumber;

    @Basic
    @Column(name = "email", nullable = true, length = -1)
    private String email;

    @Basic
    @Column(name = "zip_code", nullable = true, length = -1)
    private String zipCode;

    @Basic
    @Column(name = "city", nullable = true, length = -1)
    private String city;

    @Basic
    @Column(name = "street", nullable = true, length = -1)
    private String street;

    @Basic
    @Column(name = "landmark", nullable = true, length = -1)
    private String landmark;

    @Basic
    @Column(name = "country_id", nullable = false, insertable = false, updatable = false)
    private Long countryId;

    @Basic
    @Column(name = "state_id", nullable = false)
    private Long stateId;

    @Basic
    @Column(name = "province_id", nullable = false)
    private Long provinceId;

    @Basic
    @Column(name = "person_id", nullable = false, insertable = false, updatable = false)
    private Long personId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false, insertable = true, updatable = false)
    private Country countryByCountryId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private State stateByStateId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Province provinceByProvinceId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false, insertable = true, updatable = false)
    private Person personByPersonId;
}
