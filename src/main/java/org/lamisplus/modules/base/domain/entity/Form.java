package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@Table(name = "form", schema = "public", catalog = "lamisplus")
@EqualsAndHashCode
public class Form extends JsonBEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "code")
    @NotNull
    private String code;

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "resource_object", nullable = false, columnDefinition = "jsonb")
    private Object resourceObject;

    @Basic
    @Column(name = "resource_path")
    private String resourcePath;

    @Basic
    @Column(name = "program_code")
    private String programCode;

    @Basic
    @Column(name = "version")
    private String version;

    @Basic
    @Column(name = "name")
    private String name;

/*
    @OneToMany(mappedBy = "encounterByFormCode")
    @JsonIgnore
    private Collection<Encounter> formByCode;
*/

    @ManyToOne
    @JoinColumn(name = "program_code", referencedColumnName = "code", insertable = false, updatable = false)
    public Program formByProgramCode;


}
