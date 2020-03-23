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
@RequestMapping("/api/province")
@Slf4j
@RequiredArgsConstructor
public class ProvinceController {
    private static final String ENTITY_NAME = "province";
    private final ProvinceService provinceService;

    @PostMapping
    public ResponseEntity<Province> save(@RequestBody ProvinceDTO provinceDTO) throws URISyntaxException {
        Province province = provinceService.save(provinceDTO);
            return ResponseEntity.created(new URI("/api/province/" + province.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(province.getId()))).body(province);
    }

    @PutMapping
    public ResponseEntity<Province> update(@RequestBody ProvinceDTO provinceDTO) throws URISyntaxException {
        Province province = provinceService.update(provinceDTO);
        return ResponseEntity.created(new URI("/api/province/" + province.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(province.getId())))
                .body(province);
    }

    @GetMapping
    public ResponseEntity<List<Province>> getAllProvince() {
        return ResponseEntity.ok(provinceService.allProvince());
    }

   /* @DeleteMapping("/{id}")
    public ResponseEntity<?> archiveProvince(@PathVariable Long id) {
        Optional<Province> state = this.provinceRepository.findById(id);
        if (state.isPresent()){
            Province stateArchive = state.get();
            stateArchive.setArchive(Boolean.TRUE);
        } else throw new BadRequestAlertException("Record not found with id ", ENTITY_NAME, "id is  Null");
        return ResponseEntity.ok().build();
    }*/

    @GetMapping("/{stateId}")
    public ResponseEntity<List<Province>> getAllProvinceByStateId(@PathVariable Long stateId) {
        return ResponseEntity.ok(provinceService.allProvinceBy(stateId));
    }
}
