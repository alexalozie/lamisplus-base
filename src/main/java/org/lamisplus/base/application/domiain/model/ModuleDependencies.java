package org.lamisplus.base.application.domiain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "module_dependencies", schema = "public", catalog = "lamisplus")
public class ModuleDependencies {
    private Long id;
    private String version;
    private Long dependencyId;
    private Long moduleId;
    private Module moduleByDependencyId;
    private Module moduleByModuleId;

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
    @Column(name = "version", nullable = false, length = 255)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "dependency_id", nullable = false, insertable = false, updatable = false)
    public Long getDependencyId() {
        return dependencyId;
    }

    public void setDependencyId(Long dependencyId) {
        this.dependencyId = dependencyId;
    }

    @Basic
    @Column(name = "module_id", nullable = false, insertable = false, updatable = false)
    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleDependencies that = (ModuleDependencies) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(version, that.version) &&
                Objects.equals(dependencyId, that.dependencyId) &&
                Objects.equals(moduleId, that.moduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, dependencyId, moduleId);
    }

    @ManyToOne
    @JoinColumn(name = "dependency_id", referencedColumnName = "id", nullable = false)
    public Module getModuleByDependencyId() {
        return moduleByDependencyId;
    }

    public void setModuleByDependencyId(Module moduleByDependencyId) {
        this.moduleByDependencyId = moduleByDependencyId;
    }

    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Module getModuleByModuleId() {
        return moduleByModuleId;
    }

    public void setModuleByModuleId(Module moduleByModuleId) {
        this.moduleByModuleId = moduleByModuleId;
    }
}
