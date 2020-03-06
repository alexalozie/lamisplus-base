package org.lamisplus.base.domain.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PersonResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String otherNames;
    private String title;
    private LocalDate dob;
    private Boolean dobEstimated;
    private String gender;
    private String education;
    private String occupation;
    private String maritalStatusId;
    private String personTitleId;

}
