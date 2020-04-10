package org.lamisplus.modules.base.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "permission", schema = "public", catalog = "lamisplus")
public class Priviledge {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "level", nullable = true, length = -1)
    private String priviledgeLevel;

    @Basic
    @Column(name = "menu_name")
    private String menuName;

    @Basic
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "priviledgeByPriviledgeId")
    private Collection<RoleHasPriviledge> roleHasPriviledgesById;

    @OneToMany(mappedBy = "priviledgeByPriviledgeId")
    private Collection<UserHasPriviledge> userHasPriviledgesById;

}
