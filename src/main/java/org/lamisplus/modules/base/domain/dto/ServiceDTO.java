package org.lamisplus.modules.base.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.lamisplus.modules.base.domain.entities.ServiceEnrollment;

import java.util.Collection;

@Data
public class ServiceDTO {

    private Long id;

    private String programCode;

    private Long moduleId;

    @JsonIgnore
    public Collection<ServiceEnrollment> ServiceEnrollmentsById;
}
