package org.lamisplus.base.application.domiain.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "identifier_type", schema = "public", catalog = "lamisplus")
public class IdentifierType {
    private Long id;
    private String IdentifierTypeName;
    private String validator;
    private Collection<ServiceEnrollment> serviceEnrollmentsById;

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
    @Column(name = "name", nullable = true, length = 255)
    public String getIdentifierTypeName() {
        return IdentifierTypeName;
    }

    public void setIdentifierTypeName(String name) {
        this.IdentifierTypeName = IdentifierTypeName;
    }

    @Basic
    @Column(name = "validator", nullable = true, length = 255)
    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentifierType that = (IdentifierType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(IdentifierTypeName, that.IdentifierTypeName) &&
                Objects.equals(validator, that.validator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, IdentifierTypeName, validator);
    }

    @OneToMany(mappedBy = "identifierTypeByIdentifierTypeId")
    public Collection<ServiceEnrollment> getServiceEnrollmentsById() {
        return serviceEnrollmentsById;
    }

    public void setServiceEnrollmentsById(Collection<ServiceEnrollment> serviceEnrollmentsById) {
        this.serviceEnrollmentsById = serviceEnrollmentsById;
    }
}
