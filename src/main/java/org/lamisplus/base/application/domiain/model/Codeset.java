package org.lamisplus.base.application.domiain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "application_codeset", schema = "public", catalog = "lamisplus")
public class Codeset {
    private Long id;
    private String codesetGroup;
    private String version;
    private String language;
    private String display;
    private Integer active;
    private String code;

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
    @Column(name = "codeset_group", nullable = true, length = -1)
    public String getCodesetGroup() {
        return codesetGroup;
    }

    public void setCodesetGroup(String codesetGroup) {
        this.codesetGroup = codesetGroup;
    }

    @Basic
    @Column(name = "version", nullable = true, length = -1)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "language", nullable = true, length = -1)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "display", nullable = true, length = -1)
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Basic
    @Column(name = "active", nullable = true)
    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    @Basic
    @Column(name = "code", nullable = true, length = -1)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Codeset that = (Codeset) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(codesetGroup, that.codesetGroup) &&
                Objects.equals(version, that.version) &&
                Objects.equals(language, that.language) &&
                Objects.equals(display, that.display) &&
                Objects.equals(active, that.active) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codesetGroup, version, language, display, active, code);
    }


}
