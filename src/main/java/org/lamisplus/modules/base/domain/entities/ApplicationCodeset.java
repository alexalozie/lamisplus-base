package org.lamisplus.modules.base.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@Data
@Entity
@Table(name = "application_codeset", schema = "public", catalog = "lamisplus2")
@EqualsAndHashCode
public class ApplicationCodeset {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 1L;

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
    @Column(name = "archived", nullable = true)
    @JsonIgnore
    private Integer archived;

}
