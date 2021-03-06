package org.lamisplus.modules.base.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entity.Drug;
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
public class DrugService {
    private final DrugRepository drugRepository;
    private final DrugGroupRepository drugGroupRepository;


    public List<Drug> getAllDrugs() {
        return this.drugRepository.findAll();
    }

    public Drug save(Drug drug) {
        Optional<Drug> DrugOptional = drugRepository.findByBrandName(drug.getBrandName());
        if (DrugOptional.isPresent())
            throw new RecordExistException(Drug.class, "Brand Name", drug.getBrandName());
        return drugRepository.save(drug);
    }

    public Drug getDrug(Long id) {
        Optional<Drug> drugOptional = this.drugRepository.findById(id);
        if (!drugOptional.isPresent()) throw new EntityNotFoundException(Drug.class, "Id", id + "");
        return drugOptional.get();
    }

    public Drug update(Long id, Drug drug) {
        Optional<Drug> drugOptional = drugRepository.findById(id);
        if(!drugOptional.isPresent())throw new EntityNotFoundException(Drug.class, "Id", id +"");
        drug.setId(id);
        return drugRepository.save(drug);
    }

    public Boolean delete(Long id, Drug drug) {
        return true;
    }

}
