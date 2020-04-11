package org.lamisplus.modules.base.controller;


import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.lamisplus.modules.base.domain.dto.ServiceDTO;
import org.lamisplus.modules.base.domain.entity.Program;
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
    public ResponseEntity<Program> save(@RequestBody ServiceDTO serviceDTO) throws URISyntaxException {
        Program program = this.programService.save(serviceDTO);
        return ResponseEntity.created(new URI("/api/programs/" + program.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(program.getId()))).body(program);
    }

    @GetMapping
    public ResponseEntity<List<Program>> getAllPrograms() {
            return ResponseEntity.ok(this.programService.getAllPrograms());

    }
}
