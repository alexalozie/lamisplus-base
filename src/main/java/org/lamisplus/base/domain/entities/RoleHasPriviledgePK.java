package org.lamisplus.base.domain.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class RoleHasPriviledgePK implements Serializable {
    private Long roleId;
    private Long priviledgeId;

    @Column(name = "role_id", nullable = false, insertable = false, updatable = false)
    @Id
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "priviledge_id", nullable = false, insertable = false, updatable = false)
    @Id
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
        RoleHasPriviledgePK that = (RoleHasPriviledgePK) o;
        return Objects.equals(roleId, that.roleId) &&
                Objects.equals(priviledgeId, that.priviledgeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, priviledgeId);
    }
}
