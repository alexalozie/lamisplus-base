package org.lamisplus.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@Table(name = "facility", schema = "public", catalog = "lamisplus")
@EqualsAndHashCode
public class Facility {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "facility_code", nullable = true, length = 255)
    private String facilityCode;

    @Basic
    @Column(name = "facility_level", nullable = true, length = 255)
    private String facilityLevel;

    @Basic
    @Column(name = "facility_level_option", nullable = true, length = 255)
    private String facilityLevelOption;

    @Basic
    @Column(name = "facility_name", nullable = true, length = 255)
    private String facilityName;

    @Basic
    @Column(name = "latitude")
    @NotNull
    private String latitude;

    @Basic
    @Column(name = "longitude")
    private String longitude;

    @Basic
    @Column(name = "ownership")
    @NotNull
    private String ownership;

    @Basic
    @Column(name = "ownership_type")
    @NotNull
    private String ownershipType;

    @Basic
    @Column(name = "physical_location")
    @NotNull
    private String physicalLocation;

    @Basic
    @Column(name = "postal_address")
    @NotNull
    private String postalAddress;

    @Basic
    @Column(name = "start_date")
    @NotNull
    private Date startDate;
    //private Collection<FacilityService> facilityServicesById;
    //private Collection<Patient> patientsById;


   /*
    @OneToMany(mappedBy = "facilityByFacilityId")
    public Collection<FacilityService> getFacilityServicesById() {
        return facilityServicesById;
    }

    public void setFacilityServicesById(Collection<FacilityService> facilityServicesById) {
        this.facilityServicesById = facilityServicesById;
    }

    @OneToMany(mappedBy = "facilityByFacilityId")
    public Collection<Patient> getPatientsById() {
        return patientsById;
    }

    public void setPatientsById(Collection<Patient> patientsById) {
        this.patientsById = patientsById;
    }

    */
}
