package org.lamisplus.base.application.domiain.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Facility {
    private Long id;
    private String facilityCode;
    private String facilityLevel;
    private String facilityLevelOption;
    private String facilityName;
    private String latitude;
    private String longitude;
    private String ownership;
    private String ownershipType;
    private String physicalLocation;
    private String postalAddress;
    private Date startDate;
    private Collection<FacilityService> facilityServicesById;
    private Collection<Patient> patientsById;

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
    @Column(name = "facility_code", nullable = true, length = 255)
    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    @Basic
    @Column(name = "facility_level", nullable = true, length = 255)
    public String getFacilityLevel() {
        return facilityLevel;
    }

    public void setFacilityLevel(String facilityLevel) {
        this.facilityLevel = facilityLevel;
    }

    @Basic
    @Column(name = "facility_level_option", nullable = true, length = 255)
    public String getFacilityLevelOption() {
        return facilityLevelOption;
    }

    public void setFacilityLevelOption(String facilityLevelOption) {
        this.facilityLevelOption = facilityLevelOption;
    }

    @Basic
    @Column(name = "facility_name", nullable = true, length = 255)
    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    @Basic
    @Column(name = "latitude", nullable = true, length = 255)
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = true, length = 255)
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "ownership", nullable = true, length = 255)
    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    @Basic
    @Column(name = "ownership_type", nullable = true, length = 255)
    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    @Basic
    @Column(name = "physical_location", nullable = true, length = 255)
    public String getPhysicalLocation() {
        return physicalLocation;
    }

    public void setPhysicalLocation(String physicalLocation) {
        this.physicalLocation = physicalLocation;
    }

    @Basic
    @Column(name = "postal_address", nullable = true, length = 255)
    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Basic
    @Column(name = "start_date", nullable = true)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility facility = (Facility) o;
        return Objects.equals(id, facility.id) &&
                Objects.equals(facilityCode, facility.facilityCode) &&
                Objects.equals(facilityLevel, facility.facilityLevel) &&
                Objects.equals(facilityLevelOption, facility.facilityLevelOption) &&
                Objects.equals(facilityName, facility.facilityName) &&
                Objects.equals(latitude, facility.latitude) &&
                Objects.equals(longitude, facility.longitude) &&
                Objects.equals(ownership, facility.ownership) &&
                Objects.equals(ownershipType, facility.ownershipType) &&
                Objects.equals(physicalLocation, facility.physicalLocation) &&
                Objects.equals(postalAddress, facility.postalAddress) &&
                Objects.equals(startDate, facility.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, facilityCode, facilityLevel, facilityLevelOption, facilityName, latitude, longitude, ownership, ownershipType, physicalLocation, postalAddress, startDate);
    }

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
}
