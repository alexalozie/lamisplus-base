package org.lamisplus.modules.base.base.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.lamisplus.modules.base.base.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.base.apierror.RecordExistException;
import org.lamisplus.modules.base.base.domiain.dto.BadRequestAlertException;
import org.lamisplus.modules.base.base.domiain.dto.HeaderUtil;
import org.lamisplus.modules.base.base.domiain.model.Province;
import org.lamisplus.modules.base.base.domiain.model.State;
import org.lamisplus.modules.base.base.repository.ProvinceRepository;
import org.lamisplus.modules.base.base.repository.StateRepository;
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
    private final ProvinceRepository provinceRepository;
    private final StateRepository stateRepository;

    private static Object exist(Province o) {
        throw new RecordExistException(Province.class, "Id", o.getName());
    }

    private static Province notExit() {
        throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id is null");
    }

    @PostMapping
    public ResponseEntity<Province> saveProvince(@RequestBody Province province) throws URISyntaxException {
        provinceRepository.findById(province.getId()).map(ProvinceController::exist);
        Optional<State> state = this.stateRepository.findById(province.getStateByStateId().getId());
        if (state.isPresent()){
            State state1 = state.get();
            province.setStateByStateId(state1);
            Province result = this.provinceRepository.save(province);
            return ResponseEntity.created(new URI("/api/province/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId()))).body(result);
        } else throw new BadRequestAlertException("State id did not exist ", ENTITY_NAME, "id is null");

    }

    @PutMapping
    public ResponseEntity<Province> updateProvince(@RequestBody Province province) throws URISyntaxException {
        Optional<Province> province11 = this.provinceRepository.findById(province.getId());
        province11.orElseGet(ProvinceController::notExit);
        State state = this.stateRepository.getOne(province.getStateByStateId().getId());
        province.setStateByStateId(state);
        Province result = this.provinceRepository.save(province);
        return ResponseEntity.created(new URI("/api/province/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(result.getId())))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<Province>> getAllProvince() {
        return ResponseEntity.ok(this.provinceRepository.findAll());
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
    public ResponseEntity<List<Province>> getAllProvince(@PathVariable Long stateId) {
        List<Province> stateSet = this.provinceRepository.findByStateId(stateId);
        if(stateSet.size()< 1)
            throw new EntityNotFoundException(Province.class, "State Number", stateId.toString());
        System.out.println("Entered all province by state Id");
        return ResponseEntity.ok(this.provinceRepository.findByStateId(stateId));
    }


}
