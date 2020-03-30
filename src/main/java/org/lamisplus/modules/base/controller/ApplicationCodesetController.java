package org.lamisplus.modules.base.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.dto.ApplicationCodesetDTO;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.lamisplus.modules.base.domain.entities.ApplicationCodeset;
import org.lamisplus.modules.base.service.ApplicationCodesetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/codeset")
@Slf4j
@RequiredArgsConstructor
public class ApplicationCodesetController {
    private final ApplicationCodesetService applicationCodesetService;
    private static String ENTITY_NAME = "ApplicationCodeset";

    //.........
    @GetMapping("/{group}")
    public ResponseEntity<List<ApplicationCodeset>> getApplicationCodesetByGroup(@PathVariable String group) {
        return ResponseEntity.ok(this.applicationCodesetService.getApplicationCodesetByGroup(group));
    }

    //.........
    @GetMapping
    public ResponseEntity<List<ApplicationCodeset>> getAllApplicationCodeset() {
        return ResponseEntity.ok(this.applicationCodesetService.getAllApplicationCodeset());
    }

    @PostMapping
    public ResponseEntity<ApplicationCodeset> save(@RequestBody ApplicationCodesetDTO applicationCodesetDTO) throws URISyntaxException {
        ApplicationCodeset applicationCodeset = applicationCodesetService.save(applicationCodesetDTO);
        return ResponseEntity.created(new URI("/api/encounter/" + applicationCodeset.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(applicationCodeset.getId()))).body(applicationCodeset);
    }
}
