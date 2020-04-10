package org.lamisplus.modules.base.controller;


import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.lamisplus.modules.base.domain.dto.ModuleDTO;
import org.lamisplus.modules.base.domain.entity.Module;
import org.lamisplus.modules.base.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/modules")
public class ModuleController {
    private final String ENTITY_NAME = "Module";
    private final ModuleService moduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Module> save(@RequestBody ModuleDTO moduleDTO) throws URISyntaxException {
        Module module = this.moduleService.save(moduleDTO);
        return ResponseEntity.created(new URI("/api/modules/" + module.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(module.getId()))).body(module);
    }

    @GetMapping
    public ResponseEntity<List<Module>> getAllModule() {
        return ResponseEntity.ok(this.moduleService.getAllModules());
    }
}
