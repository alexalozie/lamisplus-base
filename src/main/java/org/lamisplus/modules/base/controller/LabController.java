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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/laboratory")
@Slf4j
@RequiredArgsConstructor
public class LabController {
    private final LabService labService;

    //LAB TEST GROUP.........
    @GetMapping("/labtest-Group")
    public ResponseEntity<List<LabTestGroup>> getAllLabTestGroup() {
        return ResponseEntity.ok(this.labService.getAllLabTestGroup());
    }

    //LAB TEST.........
    @GetMapping("/{labTestGroupId}/labtest")
    public ResponseEntity<List<LabTestDTO>> getAllLabTest(@PathVariable Long labTestGroupId) {
        return ResponseEntity.ok(this.labService.getAllLabTest(labTestGroupId));
    }
}
