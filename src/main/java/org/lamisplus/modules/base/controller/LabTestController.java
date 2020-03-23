package org.lamisplus.modules.base.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.dto.LabTestDTO;
import org.lamisplus.modules.base.domain.entities.LabTest;
import org.lamisplus.modules.base.domain.entities.LabTestGroup;
import org.lamisplus.modules.base.repository.LabTestGroupRepository;
import org.lamisplus.modules.base.repository.LabTestRepository;
import org.lamisplus.modules.base.service.LabService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labTest")
@Slf4j
@RequiredArgsConstructor
public class LabTestController {
    private final LabService labService;

    //LAB TEST.........
    @GetMapping
    public ResponseEntity<List<LabTestDTO>> getAllLabTest(@RequestParam Long labTestGroupId) {
        return ResponseEntity.ok(this.labService.getAllLabTest(labTestGroupId));
    }
}
