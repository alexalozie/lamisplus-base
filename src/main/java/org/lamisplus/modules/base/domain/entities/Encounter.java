package org.lamisplus.modules.base.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.lamisplus.modules.base.util.converter.LocalDateConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "encounter", schema = "public", catalog = "lamisplus")
@EqualsAndHashCode
public class Encounter extends JsonBEntity implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "form_data", nullable = false, columnDefinition = "jsonb")
    private Object formData;

    @Basic
    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Basic
    @Column(name = "visit_id", nullable = false)
    private Long visitId;

    @Basic
    @Column(name = "form_name", nullable = false, length = -1)
    private String formName;

    @Basic
    @Column(name = "service_name", nullable = false, length = -1)
    private String serviceName;

    @Basic
    @Column(name = "encounter_date")
    @Convert(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd:MM:yyyy")
    private LocalDate dateEncounter;

    @Basic
    @Column(name = "time_created", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime timeCreated;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Patient patientByPatientId;

    @ManyToOne
    @JoinColumn(name = "visit_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Visit visitByVisitId;


}
