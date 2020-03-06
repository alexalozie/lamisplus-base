package org.lamisplus.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "menu", schema = "public", catalog = "lamisplus")
public class Menu {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "child_id", nullable = true)
    private Long childId;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "module_name")
    private String moduleName;

    @Basic
    @Column(name = "display_name")
    private String displayName;

    @Basic
    @Column(name = "description")
    private String description;

}
