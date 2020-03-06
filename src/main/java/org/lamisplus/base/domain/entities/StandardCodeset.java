package org.lamisplus.base.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "standard_codeset", schema = "public", catalog = "lamisplus")
public class StandardCodeset {
    private Long id;
    private String code;
    private String description;
    private String longName;
    private String shortName;
    private Long standardId;

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
    @Column(name = "code", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "long_name", nullable = true, length = 255)
    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    @Basic
    @Column(name = "short_name", nullable = true, length = 255)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "standard_id", nullable = true)
    public Long getStandardId() {
        return standardId;
    }

    public void setStandardId(Long standardId) {
        this.standardId = standardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardCodeset that = (StandardCodeset) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(description, that.description) &&
                Objects.equals(longName, that.longName) &&
                Objects.equals(shortName, that.shortName) &&
                Objects.equals(standardId, that.standardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, description, longName, shortName, standardId);
    }
}
