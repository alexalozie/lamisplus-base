package org.lamisplus.base.application.domiain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lamisplus.base.application.util.LocalDateConverter;
import org.lamisplus.base.application.util.LocalTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Visit")
@Data
@EqualsAndHashCode
public class Visit implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "date_visit_end", nullable = true)
    @Convert(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateVisitEnd;

    @Basic
    @Column(name = "date_visit_start", nullable = true)
    @Convert(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateVisitStart;

    @Basic
    @Column(name = "time_visit_end", nullable = true)
    @Convert(converter = LocalTimeAttributeConverter.class)
    //@Convert(converter = TimeConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "K:mm a")
    private LocalTime timeVisitEnd;

    @Basic
    @Column(name = "time_visit_start", nullable = true)
    @Convert(converter = LocalTimeAttributeConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "K:mm a")
    private LocalTime timeVisitStart;

    @Basic
    @Column(name = "date_next_appointment", nullable = true)
    @Convert(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateNextAppointment;

    @JoinColumn(name = "patient_id")
    @ManyToOne
    @JsonIgnore
    private Patient patient;

    @JoinColumn(name = "visit_type_id")
    @ManyToOne
    @JsonIgnore
    private ApplicationCodeset visitType;

}
