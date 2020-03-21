package org.lamisplus.modules.base.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.lamisplus.modules.base.util.converter.LocalDateConverter;
import org.lamisplus.modules.base.util.converter.LocalTimeAttributeConverter;

import javax.persistence.Convert;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EncounterDTO implements Serializable {
    private Long EncounterId;
    @JsonIgnore
    private Long personId;
    private Object formData;
    private String formName;
    private String serviceName;

    @Convert(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateEncounter;
    private Long patientId;
    private Long visitId;

    @Convert(converter = LocalTimeAttributeConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
    public LocalTime timeCreated;
    private String FirstName;
    private String LastName;

    @Convert(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private String OtherNames;
    private String hospitalNumber;


}
