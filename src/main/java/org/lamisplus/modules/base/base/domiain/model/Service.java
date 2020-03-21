package org.lamisplus.modules.base.base.domiain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Service {
    private Long id;
    private String serviceName;
    private Long moduleId;

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
    @Column(name = "service_name", nullable = true, length = 255)
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Basic
    @Column(name = "module_id", nullable = true)
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
        Service service = (Service) o;
        return Objects.equals(id, service.id) &&
                Objects.equals(serviceName, service.serviceName) &&
                Objects.equals(moduleId, service.moduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviceName, moduleId);
    }

}
