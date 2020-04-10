package org.lamisplus.modules.base.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.lamisplus.modules.base.util.converter.LocalDateConverter;
import org.lamisplus.modules.base.util.converter.LocalTimeAttributeConverter;

import javax.persistence.Convert;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EncounterFormDataDTO {
    @JsonIgnore
    private Long personId;
    @JsonIgnore
    private Long patientId;

    @JsonIgnore
    private Object formData;
    @JsonIgnore
    private String FirstName;
    @JsonIgnore
    private String LastName;

    @Convert(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonIgnore
    private LocalDate dob;
    @JsonIgnore
    private String OtherNames;
    @JsonIgnore
    private String hospitalNumber;

}
