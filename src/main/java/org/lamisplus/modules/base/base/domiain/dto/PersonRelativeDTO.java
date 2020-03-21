package org.lamisplus.modules.base.base.domiain.dto;

import lombok.Data;

@Data
public class PersonRelativeDTO {
    private String firstName;
    private String lastName;
    private String otherNames;
    private String email;
    private String mobilePhoneNumber;
    private String alternatePhoneNumber;
    private Long relationshipTypeId;
    private String address;
}
