package org.lamisplus.modules.base.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.domain.dto.ProvinceDTO;
import org.lamisplus.modules.base.domain.entities.Province;
import org.lamisplus.modules.base.domain.entities.State;
import org.lamisplus.modules.base.repository.ProvinceRepository;
import org.lamisplus.modules.base.repository.StateRepository;
import org.lamisplus.modules.base.domain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.domain.dto.HeaderUtil;
import org.lamisplus.modules.base.service.ProvinceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/provinces")
@Slf4j
@RequiredArgsConstructor
public class ProvinceController {
    private static final String ENTITY_NAME = "province";
    private final ProvinceService provinceService;

    @PostMapping
    public ResponseEntity<Province> save(@RequestBody ProvinceDTO provinceDTO) throws URISyntaxException {
        Province province = provinceService.save(provinceDTO);
        return ResponseEntity.created(new URI("/api/provinces/" + province.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(province.getId()))).body(province);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Province> update(@PathVariable Long id, @RequestBody Province province1) throws URISyntaxException {
        Province province = provinceService.update(id, province1);
        return ResponseEntity.created(new URI("/api/provinces/" + id))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(id)))
                .body(province);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Province> getProvince(@PathVariable Long id) throws URISyntaxException {
        Province province = provinceService.getProvince(id);
        return ResponseEntity.created(new URI("/api/provinces/" + province.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(province.getId())))
                .body(province);
    }


    @GetMapping
    public ResponseEntity<List<Province>> getAllProvinces() {
        return ResponseEntity.ok(provinceService.getAllProvinces());
    }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id, @RequestBody ProvinceDTO provinceDTO) throws RecordNotFoundException {
        return this.provinceService.delete(id, provinceDTO);
    }
}
