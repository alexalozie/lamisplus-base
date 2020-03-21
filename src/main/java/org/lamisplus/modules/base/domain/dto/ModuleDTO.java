package org.lamisplus.modules.base.domain.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ModuleDTO {

    private Long id;

    private Boolean active;

    private String artifact;

    private String basePackage;

    private Timestamp buildTime;

    private String description;

    private String moduleMap;

    private String name;

    private String umdLocation;

    private String version;
}
