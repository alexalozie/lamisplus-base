package org.lamisplus.modules.base.domain.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "identifier_type", schema = "public", catalog = "lamisplus")
@EqualsAndHashCode
public class IdentifierType {
    private Long id;
    private String IdentifierTypeName;
    private String validator;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    @NotNull
    public String getIdentifierTypeName() {
        return IdentifierTypeName;
    }

    public void setIdentifierTypeName(String name) {
        this.IdentifierTypeName = IdentifierTypeName;
    }

    @Basic
    @Column(name = "validator")
    @NotNull
    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }


}
