package org.lamisplus.base.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_has_priviledge", schema = "public", catalog = "lamisplus")
@IdClass(UserHasPriviledgePK.class)
public class UserHasPriviledge {
    private Long userId;
    private Long priviledgeId;
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
        UserHasPriviledge that = (UserHasPriviledge) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(priviledgeId, that.priviledgeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, priviledgeId);
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
    @JoinColumn(name = "priviledge_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Priviledge getPriviledgeByPriviledgeId() {
        return priviledgeByPriviledgeId;
    }

    public void setPriviledgeByPriviledgeId(Priviledge priviledgeByPriviledgeId) {
        this.priviledgeByPriviledgeId = priviledgeByPriviledgeId;
    }
}
