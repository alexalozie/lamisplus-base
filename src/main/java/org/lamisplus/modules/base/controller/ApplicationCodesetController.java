package org.lamisplus.modules.base.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.entities.ApplicationCodeset;
import org.lamisplus.modules.base.service.ApplicationCodesetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/codeset")
@Slf4j
@RequiredArgsConstructor
public class ApplicationCodesetController {
    private final ApplicationCodesetService applicationCodesetService;

    //.........
    @GetMapping("/{group}")
    public ResponseEntity<List<ApplicationCodeset>> getByCodesetGroup(@PathVariable String group) {
        return ResponseEntity.ok(this.applicationCodesetService.getAllByCodesetGroup(group));
    }

    //.........
    @GetMapping
    public ResponseEntity<List<ApplicationCodeset>> getAllApplicationCodeset() {
        return ResponseEntity.ok(this.applicationCodesetService.getAllApplicationCodeset());
    }
}
