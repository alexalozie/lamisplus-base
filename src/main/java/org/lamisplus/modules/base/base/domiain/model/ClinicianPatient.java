package org.lamisplus.modules.base.base.domiain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "clinician_patient", schema = "public", catalog = "lamisplus")
public class ClinicianPatient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Basic
    @Column(name = "visit_id")
    @NotNull(message = "Please Check in Patient")
    private Long visitId;

    @Basic
    @Column(name = "patient_id")
    @NotNull(message = "Patient Id not available")
    private Long patientId;

    @Basic
    @Column(name = "clinican_id")
    @NotNull(message = "Clinician Id not available")
    private Long clinicianId;

    @Basic
    @Column(name = "app_codeset_id")
    @NotNull(message = "Codeset Id not available")
    private Long appCodesetId;

    @JoinColumn(name = "app_codeset_id", insertable = false, updatable = false)
    @ManyToOne
    @JsonIgnore
    private ApplicationCodeset applicationCodeset;

    @JoinColumn(name = "visit_id", insertable = false, updatable = false)
    @ManyToOne
    @JsonIgnore
    private Visit visitType;

    @JoinColumn(name = "clinican_id", insertable = false, updatable = false)
    @ManyToOne
    @JsonIgnore
    private User clinicanByUserId;

    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    @ManyToOne
    @JsonIgnore
    private User patientByPatientId;
}
