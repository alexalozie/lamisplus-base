package org.lamisplus.base.domain.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class UserHasPriviledgePK implements Serializable {
    private Long userId;
    private Long priviledgeId;

    @Column(name = "user_id", nullable = false)
    @Id
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "priviledge_id", nullable = false)
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
        UserHasPriviledgePK that = (UserHasPriviledgePK) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(priviledgeId, that.priviledgeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, priviledgeId);
    }
}
