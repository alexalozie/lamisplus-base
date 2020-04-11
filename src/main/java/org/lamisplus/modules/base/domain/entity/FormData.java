package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class FormData extends JsonBEntity implements Serializable {
    private Long id;
    private Object data;
    private Long encounterId;
    private Encounter encounterByEncounterId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data", nullable = false, columnDefinition = "jsonb")
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Basic
    @Column(name = "encounter_id")
    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormData formData = (FormData) o;
        return Objects.equals(id, formData.id) &&
                Objects.equals(data, formData.data) &&
                Objects.equals(encounterId, formData.encounterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, encounterId);
    }

    @ManyToOne
    @JoinColumn(name = "encounter_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    public Encounter getEncounterByEncounterId() {
        return encounterByEncounterId;
    }

    public void setEncounterByEncounterId(Encounter encounterByEncounterId) {
        this.encounterByEncounterId = encounterByEncounterId;
    }
}
