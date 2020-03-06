package org.lamisplus.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "module", schema = "public", catalog = "lamisplus")
public class Module {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Basic
    @Column(name = "artifact")
    private String artifact;

    @Basic
    @Column(name = "base_package")
    private String basePackage;

    @Basic
    @Column(name = "build_time", nullable = true)
    private Timestamp buildTime;

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Basic
    @Column(name = "module_map")
    private String moduleMap;

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Basic
    @Column(name = "umd_location", nullable = true, length = 255)
    private String umdLocation;

    @Basic
    @Column(name = "version", nullable = true, length = 255)
    private String version;
    /*
    private Collection<ModuleDependencies> moduleDependenciesById;
    private Collection<ModuleDependencies> moduleDependenciesById_0;

     */


/*
    @OneToMany(mappedBy = "moduleByDependencyId")
    public Collection<ModuleDependencies> getModuleDependenciesById() {
        return moduleDependenciesById;
    }

    public void setModuleDependenciesById(Collection<ModuleDependencies> moduleDependenciesById) {
        this.moduleDependenciesById = moduleDependenciesById;
    }

    @OneToMany(mappedBy = "moduleByModuleId")
    public Collection<ModuleDependencies> getModuleDependenciesById_0() {
        return moduleDependenciesById_0;
    }

    public void setModuleDependenciesById_0(Collection<ModuleDependencies> moduleDependenciesById_0) {
        this.moduleDependenciesById_0 = moduleDependenciesById_0;
    }

 */
}
