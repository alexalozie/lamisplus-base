package org.lamisplus.modules.base.domain.dto;

import lombok.Data;

@Data
public class FormDTO {

    private Long id;

    private String name;

    private Object resourceObject;

    private String resourcePath;

    private String serviceName;

    private String version;

    private String displayName;
}
