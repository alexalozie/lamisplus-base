package org.lamisplus.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "role_has_priviledge", schema = "public", catalog = "lamisplus")
@IdClass(RoleHasPriviledgePK.class)
public class RoleHasPriviledge {
    private Long roleId;
    private Long priviledgeId;
    private Role roleByRoleId;
    private Priviledge priviledgeByPriviledgeId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Id
    @Column(name = "priviledge_id", nullable = false, insertable = false, updatable = false)
    public Long getPriviledgeId() {
        return priviledgeId;
    }

    public void setPriviledgeId(Long priviledgeId) {
        this.priviledgeId = priviledgeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleHasPriviledge that = (RoleHasPriviledge) o;
        return Objects.equals(roleId, that.roleId) &&
                Objects.equals(priviledgeId, that.priviledgeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, priviledgeId);
    }

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }

    @ManyToOne
    @JoinColumn(name = "priviledge_id", referencedColumnName = "id", nullable = false)
    public Priviledge getPriviledgeByPriviledgeId() {
        return priviledgeByPriviledgeId;
    }

    public void setPriviledgeByPriviledgeId(Priviledge priviledgeByPriviledgeId) {
        this.priviledgeByPriviledgeId = priviledgeByPriviledgeId;
    }
}
