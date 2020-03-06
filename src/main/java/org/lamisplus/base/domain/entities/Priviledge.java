package org.lamisplus.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "priviledge", schema = "public", catalog = "lamisplus")
public class Priviledge {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "priviledge_level", nullable = true, length = -1)
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
