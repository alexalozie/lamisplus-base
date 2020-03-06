package org.lamisplus.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "module_dependencies", schema = "public", catalog = "lamisplus")
public class ModuleDependencies {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @Basic
    @Column(name = "version")
    @NotNull
    private String version;

    @Basic
    @Column(name = "dependency_id", insertable = false, updatable = false)
    @NotNull
    private Long dependencyId;

    @Basic
    @Column(name = "module_id", insertable = false, updatable = false)
    @NotNull
    private Long moduleId;

    @ManyToOne
    @JoinColumn(name = "dependency_id", referencedColumnName = "id")
    @NotNull
    private Module moduleByDependencyId;

    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id", insertable = false, updatable = false)
    @NotNull
    private Module moduleByModuleId;

}
