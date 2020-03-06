package org.lamisplus.base.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "application_codeset_has_standard_codeset", schema = "public", catalog = "lamisplus")
@IdClass(ApplicationCodesetHasStandardCodesetPK.class)
public class ApplicationCodesetHasStandardCodeset {
    private Long applicationCodesetId;
    private Long standardCodesetId;

    @Id
    @Column(name = "application_codeset_id", nullable = false)
    public Long getApplicationCodesetId() {
        return applicationCodesetId;
    }

    public void setApplicationCodesetId(Long applicationCodesetId) {
        this.applicationCodesetId = applicationCodesetId;
    }

    @Id
    @Column(name = "standard_codeset_id", nullable = false)
    public Long getStandardCodesetId() {
        return standardCodesetId;
    }

    public void setStandardCodesetId(Long standardCodesetId) {
        this.standardCodesetId = standardCodesetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationCodesetHasStandardCodeset that = (ApplicationCodesetHasStandardCodeset) o;
        return Objects.equals(applicationCodesetId, that.applicationCodesetId) &&
                Objects.equals(standardCodesetId, that.standardCodesetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationCodesetId, standardCodesetId);
    }
}
