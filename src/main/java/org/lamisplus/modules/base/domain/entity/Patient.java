package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode
public class Patient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "date_registration", nullable = false)
    @NotNull
    private LocalDate dateRegistration;

    @Basic
    @Column(name = "facility_id", insertable = false, updatable = false)
    private Long facilityId = 1L;

    @Basic
    @Column(name = "person_id", insertable = false, updatable = false)
    private Long personId;

    @Basic
    @Column(name = "patient_number")
    private String hospitalNumber;

    @Basic
    @Column(name = "uuid")
    @JsonIgnore
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

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id")
    @JsonIgnore
    private Facility facilityByFacilityId;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @JsonIgnore
    private Person personByPersonId;

    //private Collection<Visit> visitsById;

    /*
    @OneToMany(mappedBy = "patientByPatientId")
    public List<Encounter> encountersById;

    */
}
