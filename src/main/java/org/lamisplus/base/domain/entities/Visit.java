package org.lamisplus.base.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Visit")
@Data
@EqualsAndHashCode
public class Visit {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Basic
    @Column(name = "date_visit_end", nullable = true)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd")
    private LocalDate dateVisitEnd;

    @Basic
    @Column(name = "date_visit_start", nullable = true)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd")
    private LocalDate dateVisitStart;

    @Basic
    @Column(name = "time_visit_start", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "K:mm a")
    private LocalTime timeVisitEnd;

    @Basic
    @Column(name = "time_visit_end", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "K:mm a")
    private LocalTime timeVisitStart;

    @Basic
    @Column(name = "date_next_appointment", nullable = true)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd")
    private LocalDate dateNextAppointment;

    @Basic
    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Basic
    @Column(name = "visit_type_id", nullable = false)
    private Long visitTypeId;

    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    @ManyToOne
    @JsonIgnore
    private Patient patient;

    @JoinColumn(name = "visit_type_id", insertable = false, updatable = false)
    @ManyToOne
    @JsonIgnore
    private ApplicationCodeset visit_Type;

}
