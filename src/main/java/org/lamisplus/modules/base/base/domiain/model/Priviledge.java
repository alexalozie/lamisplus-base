package org.lamisplus.modules.base.base.domiain.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Priviledge {
    private Long id;
    private String priviledgeLevel;
    private String menuName;
    private String description;
    private Collection<RoleHasPriviledge> roleHasPriviledgesById;
    private Collection<UserHasPriviledge> userHasPriviledgesById;

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
    @Column(name = "priviledge_level", nullable = true, length = -1)
    public String getPriviledgeLevel() {
        return priviledgeLevel;
    }

    public void setPriviledgeLevel(String priviledgeLevel) {
        this.priviledgeLevel = priviledgeLevel;
    }

    @Basic
    @Column(name = "menu_name", nullable = true, length = -1)
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Priviledge that = (Priviledge) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(priviledgeLevel, that.priviledgeLevel) &&
                Objects.equals(menuName, that.menuName) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priviledgeLevel, menuName, description);
    }

    @OneToMany(mappedBy = "priviledgeByPriviledgeId")
    public Collection<RoleHasPriviledge> getRoleHasPriviledgesById() {
        return roleHasPriviledgesById;
    }

    public void setRoleHasPriviledgesById(Collection<RoleHasPriviledge> roleHasPriviledgesById) {
        this.roleHasPriviledgesById = roleHasPriviledgesById;
    }

    @OneToMany(mappedBy = "priviledgeByPriviledgeId")
    public Collection<UserHasPriviledge> getUserHasPriviledgesById() {
        return userHasPriviledgesById;
    }

    public void setUserHasPriviledgesById(Collection<UserHasPriviledge> userHasPriviledgesById) {
        this.userHasPriviledgesById = userHasPriviledgesById;
    }
}
