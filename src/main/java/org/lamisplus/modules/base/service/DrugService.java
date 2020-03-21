package org.lamisplus.modules.base.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.entities.Drug;
import org.lamisplus.modules.base.repository.DrugRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DrugService {
    public final DrugRepository drugRepository;


    public List<Drug> getAllDrug() {
        return this.drugRepository.findAll();
    }


}
