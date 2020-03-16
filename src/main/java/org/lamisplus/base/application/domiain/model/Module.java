package org.lamisplus.base.application.domiain.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Module {
    private Long id;
    private Boolean active;
    private String artifact;
    private String basePackage;
    private Timestamp buildTime;
    private String description;
    private String moduleMap;
    private String name;
    private String umdLocation;
    private String version;
    private Collection<ModuleDependencies> moduleDependenciesById;
    private Collection<ModuleDependencies> moduleDependenciesById_0;

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
    @Column(name = "active", nullable = false)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "artifact", nullable = true, length = 255)
    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    @Basic
    @Column(name = "base_package", nullable = true, length = 255)
    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @Basic
    @Column(name = "build_time", nullable = true)
    public Timestamp getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Timestamp buildTime) {
        this.buildTime = buildTime;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "module_map", nullable = true, length = 255)
    public String getModuleMap() {
        return moduleMap;
    }

    public void setModuleMap(String moduleMap) {
        this.moduleMap = moduleMap;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "umd_location", nullable = true, length = 255)
    public String getUmdLocation() {
        return umdLocation;
    }

    public void setUmdLocation(String umdLocation) {
        this.umdLocation = umdLocation;
    }

    @Basic
    @Column(name = "version", nullable = true, length = 255)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(id, module.id) &&
                Objects.equals(active, module.active) &&
                Objects.equals(artifact, module.artifact) &&
                Objects.equals(basePackage, module.basePackage) &&
                Objects.equals(buildTime, module.buildTime) &&
                Objects.equals(description, module.description) &&
                Objects.equals(moduleMap, module.moduleMap) &&
                Objects.equals(name, module.name) &&
                Objects.equals(umdLocation, module.umdLocation) &&
                Objects.equals(version, module.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, artifact, basePackage, buildTime, description, moduleMap, name, umdLocation, version);
    }

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
}
