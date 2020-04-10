package org.lamisplus.modules.base.domain.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "regimen_line", schema = "public", catalog = "lamisplus")
public class RegimenLine {
    private Long id;
    private String name;
    private Collection<Regimen> regimenById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegimenLine that = (RegimenLine) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
/*
    @OneToMany(mappedBy = "regimenLineByRegimenLineId")
    public Collection<Regimen> getRegimenById() {
        return regimenById;
    }

    public void setRegimenById(Collection<Regimen> regimenById) {
        this.regimenById = regimenById;
    }*/
}
