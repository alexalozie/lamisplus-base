package org.lamisplus.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@Data
@Entity
@Table(name = "application_codeset", schema = "public", catalog = "lamisplus")
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

}
