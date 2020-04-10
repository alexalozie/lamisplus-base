package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@EqualsAndHashCode
public class ApplicationCodeset {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "codeset_group", nullable = true)
    private String codesetGroup;

    @Basic
    @Column(name = "version", nullable = true)
    private String version;

    @Basic
    @Column(name = "language", nullable = true)
    private String language;

    @Basic
    @Column(name = "display", nullable = true)
    private String display;

    @Basic
    @Column(name = "active", nullable = true)
    private Integer active;

    @Basic
    @Column(name = "code", nullable = true)
    private String code;

    @Basic
    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Basic
    @Column(name = "created_by")
    private String createdBy;

    @Basic
    @Column(name = "date_modified")
    private Timestamp dateModified;

    @Basic
    @Column(name = "modified_by")
    private String modifiedBy;

    @Basic
    @Column(name = "archived", nullable = true)
    @JsonIgnore
    private Integer archived;

}
