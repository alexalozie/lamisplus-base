package org.lamisplus.modules.base.domain.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_permission", schema = "public", catalog = "lamisplus")
@IdClass(UserHasPriviledgePK.class)
public class UserHasPriviledge {
    private Long userId;
    private Long permissionId;
    private User userByUserId;
    private Priviledge priviledgeByPriviledgeId;

    @Id
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "permission_id", nullable = false, insertable = false, updatable = false)
    public Long getPriviledgeId() {
        return permissionId;
    }

    public void setPriviledgeId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHasPriviledge that = (UserHasPriviledge) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, permissionId);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Priviledge getPriviledgeByPriviledgeId() {
        return priviledgeByPriviledgeId;
    }

    public void setPriviledgeByPriviledgeId(Priviledge priviledgeByPriviledgeId) {
        this.priviledgeByPriviledgeId = priviledgeByPriviledgeId;
    }
}
