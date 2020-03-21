package org.lamisplus.modules.base.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.dto.FormDTO;
import org.lamisplus.modules.base.domain.entities.Form;
import org.lamisplus.modules.base.service.FormService;

import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/forms")
@Slf4j
@RequiredArgsConstructor
public class FormController {
    private final FormService formService;

    @GetMapping("/all")
    public List<FormDTO> getAllForms() {
        return this.formService.getAllForm();
    }


}
