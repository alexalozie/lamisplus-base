package org.lamisplus.modules.base.controller;


import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.lamisplus.modules.base.domain.dto.ServiceDTO;
import org.lamisplus.modules.base.domain.entities.Service;
import org.lamisplus.modules.base.repository.ModuleRepository;
import org.lamisplus.modules.base.service.ProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/programs")
public class ProgramController {
    private final String ENTITY_NAME = "Program";
    private final ProgramService programService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Service> save(@RequestBody ServiceDTO serviceDTO) throws URISyntaxException {
        Service service = this.programService.save(serviceDTO);
        return ResponseEntity.created(new URI("/api/programs/" + service.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(service.getId()))).body(service);
    }

    @GetMapping
    public ResponseEntity<List<Service>> getServices(@RequestParam(required = false) Long moduleId) {
        if(moduleId == null || moduleId == 0) {
            return ResponseEntity.ok(this.programService.getServiceByModuleId(moduleId));
        }else {
            return ResponseEntity.ok(this.programService.getAllService());
        }
    }
}
