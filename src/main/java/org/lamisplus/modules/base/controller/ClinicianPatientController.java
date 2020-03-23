package org.lamisplus.modules.base.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.ClinicianPatientDTO;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.lamisplus.modules.base.domain.entities.ClinicianPatient;
import org.lamisplus.modules.base.service.ClinicianPatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/assignPatients")
@Slf4j
@RequiredArgsConstructor
public class ClinicianPatientController {
    private static String ENTITY_NAME = "Clinician";
    private final ClinicianPatientService clinicianPatientService;

    //ASSIGNING A CLINICIAN
    @PostMapping
    public ResponseEntity<ClinicianPatient> assignClinician(@RequestBody ClinicianPatientDTO clinicianPatientDTO) throws URISyntaxException {
        ClinicianPatient clinicianPatient1 = clinicianPatientService.assignClinician(clinicianPatientDTO);
        return ResponseEntity.created(new URI("/api/assignPatient" + clinicianPatient1.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(clinicianPatient1.getId()))).body(clinicianPatient1);
    }
}
