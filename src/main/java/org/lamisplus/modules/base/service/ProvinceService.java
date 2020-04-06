package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.ProvinceDTO;
import org.lamisplus.modules.base.domain.entities.Province;
import org.lamisplus.modules.base.domain.entities.State;
import org.lamisplus.modules.base.domain.mapper.ProvinceMapper;
import org.lamisplus.modules.base.repository.ProvinceRepository;
import org.lamisplus.modules.base.repository.StateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProvinceService {

    private static final String ENTITY_NAME = "province";
    private final ProvinceRepository provinceRepository;
    private final StateRepository stateRepository;
    private final ProvinceMapper provinceMapper;

    private static Object exist(Class o, String Param1, String Param2) {
        throw new RecordExistException(o, Param1, Param2);
    }

    private static Object notExit(Class o, String Param1, String Param2) {
        throw new EntityNotFoundException(o, Param1, Param2);
    }


    public Province save(ProvinceDTO provinceDTO) {
        List<Province> provinceList = provinceRepository.findByStateId(provinceDTO.getStateId());
        if (provinceList.size() < 1 || provinceList == null)
            notExit(Province.class, "State Id", provinceDTO.getStateId() + "");

        Optional<Province> provinceOptional = provinceRepository.findByName(provinceDTO.getName());
        if (provinceOptional.isPresent()) exist(Province.class, "State Id", provinceDTO.getStateId() + "");

        Province province = provinceMapper.toProvinceDTO(provinceDTO);

        return provinceRepository.save(province);
    }

    public List<Province> getAllProvinces(){
        return provinceRepository.findAll();
    }

    public List<Province> getAllProvinceByStateId(Long id){
        List<Province> stateSet = this.provinceRepository.findByStateId(id);
        if(stateSet.size()< 1 || stateSet == null) notExit(Province.class, "State Id", id + "");
        log.info("Entered all province by state Id");

        return stateSet;
    }

    public Province update(Long id, Province province) {
        Optional<Province> provinceOptional = provinceRepository.findById(id);
        if(!provinceOptional.isPresent())throw new EntityNotFoundException(Province.class, "Id", id +"");
        province.setId(id);
        return provinceRepository.save(province);
    }

    public Province getProvince(Long id){
        Optional<Province> provinceOptional = provinceRepository.findById(id);
        if (!provinceOptional.isPresent()) notExit(Province.class, "Id", id + "");

        return provinceRepository.save(provinceOptional.get());
    }


    //To do
    public Boolean delete(Long id, ProvinceDTO provinceDTO) {
        return true;
    }
}
