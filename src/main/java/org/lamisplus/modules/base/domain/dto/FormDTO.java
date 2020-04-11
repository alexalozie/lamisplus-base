package org.lamisplus.modules.base.domain.dto;

import lombok.Data;

@Data
public class FormDTO {
    
    private Long id;

    private String name;

    private String code;

    private Object resourceObject;

    private String resourcePath;

    private String programCode;

    private Double version;
}
