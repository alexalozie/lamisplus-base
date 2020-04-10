package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Regimen {
    private Long id;
    private String name;
    private Long regimenLineId;
    private RegimenLine regimenLineByRegimenLineId;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "regimen_line_id")
    public Long getRegimenLineId() {
        return regimenLineId;
    }

    public void setRegimenLineId(Long regimenLineId) {
        this.regimenLineId = regimenLineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Regimen regimen = (Regimen) o;
        return Objects.equals(id, regimen.id) &&
                Objects.equals(name, regimen.name) &&
                Objects.equals(regimenLineId, regimen.regimenLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, regimenLineId);
    }

    @ManyToOne
    @JoinColumn(name = "regimen_line_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    public RegimenLine getRegimenLineByRegimenLineId() {
        return regimenLineByRegimenLineId;
    }

    public void setRegimenLineByRegimenLineId(RegimenLine regimenLineByRegimenLineId) {
        this.regimenLineByRegimenLineId = regimenLineByRegimenLineId;
    }
}
