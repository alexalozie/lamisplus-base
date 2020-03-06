package org.lamisplus.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "service", schema = "public", catalog = "lamisplus")
public class Service {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "service_name")
    private String serviceName;

    @Basic
    @Column(name = "module_id")
    private Long moduleId;



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
