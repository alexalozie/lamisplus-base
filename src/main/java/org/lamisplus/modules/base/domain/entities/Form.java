package org.lamisplus.modules.base.domain.entities;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "form", schema = "public", catalog = "lamisplus")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@EqualsAndHashCode
public class Form extends JsonBEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    @NotNull
    private String name;

    @Type(type = "json")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "resource_object")
    private Object resourceObject;

    @Basic
    @Column(name = "resource_path")
    private String resourcePath;

    @Basic
    @Column(name = "service_name")
    private String serviceName;

    @Basic
    @Column(name = "version")
    private String version;

    @Basic
    @Column(name = "display_name")
    private String displayName;

}
