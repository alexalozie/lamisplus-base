package org.lamisplus.base.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Standard {
    private Long id;
    private String name;

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
    @Column(name = "name", nullable = true, length = -1)
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
        Standard standard = (Standard) o;
        return Objects.equals(id, standard.id) &&
                Objects.equals(name, standard.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
