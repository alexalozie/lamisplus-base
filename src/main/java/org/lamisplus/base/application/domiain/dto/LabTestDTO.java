package org.lamisplus.base.application.domiain.dto;


import lombok.Data;

@Data
public class LabTestDTO {

    private Long id;

    private Long labTestCategoryId;

    private String description;

    private String unitMeasurement;

}
