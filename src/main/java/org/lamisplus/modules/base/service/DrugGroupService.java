package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entity.Drug;
import org.lamisplus.modules.base.domain.entity.DrugGroup;
import org.lamisplus.modules.base.repository.DrugGroupRepository;
import org.lamisplus.modules.base.repository.DrugRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DrugGroupService {
    private final DrugRepository drugRepository;
    private final DrugGroupRepository drugGroupRepository;

    //List of Lab Test
    public List<DrugGroup> getAllDrugGroups() {
        return this.drugGroupRepository.findAll();
    }

    public DrugGroup save(DrugGroup drugGroup) {
        Optional<DrugGroup> drugOptional = drugGroupRepository.findById(drugGroup.getId());
        if (drugOptional.isPresent())
            throw new RecordExistException(DrugGroup.class, "Id", drugGroup.getId() + "");
        return drugGroupRepository.save(drugGroup);
    }

    public DrugGroup getDrugGroup(Long id) {
        Optional<DrugGroup> drugOptional = this.drugGroupRepository.findById(id);
        if (!drugOptional.isPresent()) throw new EntityNotFoundException(DrugGroup.class, "Id", id + "");
        return drugOptional.get();
    }

    public DrugGroup update(Long id, DrugGroup drugGroup) {
        Optional<DrugGroup> drugOptional = drugGroupRepository.findById(id);
        if(!drugOptional.isPresent())throw new EntityNotFoundException(DrugGroup.class, "Id", id +"");
        drugGroup.setId(id);
        return drugGroupRepository.save(drugGroup);
    }

    public List<Drug> getDrugByDrugGroupId(Long id){
        Optional<DrugGroup> drugOptional = drugGroupRepository.findById(id);
        if(!drugOptional.isPresent())throw new EntityNotFoundException(DrugGroup.class, "Id", id +"");
        log.info("Drug group is"+drugOptional.get());
        List<Drug> drugs = drugOptional.get().getDrugsById();
        return drugs;
    }

    public Boolean delete(Long id, DrugGroup drugGroup) {
        return true;
    }

}
