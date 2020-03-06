package org.lamisplus.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "role", schema = "public", catalog = "lamisplus")
public class Role {
    private Integer id;
    private Integer name;
    private Collection<RoleHasPriviledge> roleHasPriviledgesById;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true)
    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "roleByRoleId")
    public Collection<RoleHasPriviledge> getRoleHasPriviledgesById() {
        return roleHasPriviledgesById;
    }

    public void setRoleHasPriviledgesById(Collection<RoleHasPriviledge> roleHasPriviledgesById) {
        this.roleHasPriviledgesById = roleHasPriviledgesById;
    }
}
