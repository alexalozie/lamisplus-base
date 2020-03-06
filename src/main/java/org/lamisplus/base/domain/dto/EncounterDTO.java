package org.lamisplus.base.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.lamisplus.base.domain.entities.JsonBEntity;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class EncounterDTO implements Serializable {
    @Type(type = "jsonb")
    private Object formData;

    private Long patientId;

    private Long visitId;

    private String formName;

    private String serviceName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd:MM:yyyy")
    private LocalDate encounterDate;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FormDate {");
        builder.append(formData);
        builder.append("}");
        return builder.toString();
    }

}
