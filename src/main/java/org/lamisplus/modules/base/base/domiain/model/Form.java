package org.lamisplus.modules.base.base.domiain.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "form", schema = "public", catalog = "lamisplus")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Form extends JsonBEntity implements Serializable {
    private Long id;
    private String name;
    private Object resourceObject;
    private String resourcePath;
    private String serviceName;
    private String version;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Type(type = "json")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "resource_object", nullable = true)
    public Object getResourceObject() {
        return resourceObject;
    }

    public void setResourceObject(Object resourceObject) {
        this.resourceObject = resourceObject;
    }

    @Basic
    @Column(name = "resource_path", nullable = true, length = -1)
    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Basic
    @Column(name = "service_name", nullable = true, length = -1)
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Basic
    @Column(name = "version", nullable = true, length = -1)
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
        Form form = (Form) o;
        return Objects.equals(id, form.id) &&
                Objects.equals(name, form.name) &&
                Objects.equals(resourceObject, form.resourceObject) &&
                Objects.equals(resourcePath, form.resourcePath) &&
                Objects.equals(serviceName, form.serviceName) &&
                Objects.equals(version, form.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, resourceObject, resourcePath, serviceName, version);
    }
}
