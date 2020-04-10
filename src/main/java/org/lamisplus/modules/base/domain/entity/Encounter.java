package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.lamisplus.modules.base.util.converter.LocalDateConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode
public class Encounter extends JsonBEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
/*    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "form_data", nullable = false, columnDefinition = "jsonb")
    private Object formData;*/
    @Basic
    @Column(name = "patient_id", nullable = false)
    private Long patientId;
    @Basic
    @Column(name = "visit_id", nullable = false)
    private Long visitId;
    @Basic
    @Column(name = "form_code", nullable = false)
    private String formCode;
    @Basic
    @Column(name = "program_code", nullable = false)
    private String programCode;
    @Basic
    @Column(name = "date_encounter")
    @Convert(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateEncounter;

    @Basic
    @Column(name = "date_created", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime timeCreated;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Patient patientByPatientId;

    @ManyToOne
    @JoinColumn(name = "visit_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Visit visitByVisitId;

    @ManyToOne
    @JoinColumn(name = "form_code", referencedColumnName = "code", insertable = false, updatable = false)
    @JsonIgnore
    private Form encounterByFormCode;

    @ManyToOne
    @JoinColumn(name = "program_code", referencedColumnName = "code", insertable = false, updatable = false)
    @JsonIgnore
    private Program programByProgramCode;


    @OneToMany(mappedBy = "encounterByEncounterId")
    private List<FormData> formData = new ArrayList<FormData>();

    }
