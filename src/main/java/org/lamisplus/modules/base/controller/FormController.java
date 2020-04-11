package org.lamisplus.modules.base.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.dto.FormDTO;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.lamisplus.modules.base.domain.entity.Form;
import org.lamisplus.modules.base.service.FormService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;



@RestController
@RequestMapping("/api/forms")
@Slf4j
@RequiredArgsConstructor
public class FormController {
    private final FormService formService;
    private final String ENTITY_NAME = "Form";

    @GetMapping
    public ResponseEntity<List<Form>> getAllForms() {
            return ResponseEntity.ok(this.formService.getAllForms());

    }
    @GetMapping ("/{formId}/{programCode}")
    public ResponseEntity<FormDTO> getFormByFormIdAndProgramCode(@PathVariable Long formId, @PathVariable String programCode) {
            return ResponseEntity.ok(this.formService.getFormByFormIdAndProgramCode(formId, programCode));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Form> save(@RequestBody FormDTO formDTO) throws URISyntaxException {
        Form form = this.formService.save(formDTO);
        return ResponseEntity.created(new URI("/api/forms/" + form.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(form.getId()))).body(form);
    }


}
