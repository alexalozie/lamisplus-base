package org.lamisplus.base.domain.entities;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "service_enrollment", schema = "public", catalog = "lamisplus")
public class ServiceEnrollment {
    private Long id;
    private LocalDate dateEnrollment;
    private LocalDate dateExist;
    private String identifierValue;
    private Long identifierTypeId;
    private Long patientId;
    private Long servicesId;
    private IdentifierType identifierTypeByIdentifierTypeId;
    private Patient patientByPatientId;
    private Service serviceByServicesId;

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
    @Column(name = "date_enrollment", nullable = false)
    public LocalDate getDateEnrollment() {
        return dateEnrollment;
    }

    public void setDateEnrollment(LocalDate dateEnrollment) {
        this.dateEnrollment = dateEnrollment;
    }

    @Basic
    @Column(name = "date_exist", nullable = true)
    public LocalDate getDateExist() {
        return dateExist;
    }

    public void setDateExist(LocalDate dateExist) {
        this.dateExist = dateExist;
    }

    @Basic
    @Column(name = "identifier_value", nullable = true, length = 255)
    public String getIdentifierValue() {
        return identifierValue;
    }

    public void setIdentifierValue(String identifierValue) {
        this.identifierValue = identifierValue;
    }

    @Basic
    @Column(name = "identifier_type_id", nullable = false, insertable = false, updatable = false)
    public Long getIdentifierTypeId() {
        return identifierTypeId;
    }

    public void setIdentifierTypeId(Long identifierTypeId) {
        this.identifierTypeId = identifierTypeId;
    }

    @Basic
    @Column(name = "patient_id", nullable = false, insertable = false, updatable = false)
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Basic
    @Column(name = "services_id", nullable = false, insertable = false, updatable = false)
    public Long getServicesId() {
        return servicesId;
    }

    public void setServicesId(Long servicesId) {
        this.servicesId = servicesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceEnrollment that = (ServiceEnrollment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(dateEnrollment, that.dateEnrollment) &&
                Objects.equals(dateExist, that.dateExist) &&
                Objects.equals(identifierValue, that.identifierValue) &&
                Objects.equals(identifierTypeId, that.identifierTypeId) &&
                Objects.equals(patientId, that.patientId) &&
                Objects.equals(servicesId, that.servicesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateEnrollment, dateExist, identifierValue, identifierTypeId, patientId, servicesId);
    }

    @ManyToOne
    @JoinColumn(name = "identifier_type_id", referencedColumnName = "id", nullable = false)
    public IdentifierType getIdentifierTypeByIdentifierTypeId() {
        return identifierTypeByIdentifierTypeId;
    }

    public void setIdentifierTypeByIdentifierTypeId(IdentifierType identifierTypeByIdentifierTypeId) {
        this.identifierTypeByIdentifierTypeId = identifierTypeByIdentifierTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    public Patient getPatientByPatientId() {
        return patientByPatientId;
    }

    public void setPatientByPatientId(Patient patientByPatientId) {
        this.patientByPatientId = patientByPatientId;
    }

    @ManyToOne
    @JoinColumn(name = "services_id", referencedColumnName = "id", nullable = false)
    public Service getServiceByServicesId() {
        return serviceByServicesId;
    }

    public void setServiceByServicesId(Service serviceByServicesId) {
        this.serviceByServicesId = serviceByServicesId;
    }
}
