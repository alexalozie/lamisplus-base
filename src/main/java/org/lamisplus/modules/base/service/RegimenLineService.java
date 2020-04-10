package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entity.Regimen;
import org.lamisplus.modules.base.domain.entity.RegimenLine;
import org.lamisplus.modules.base.repository.RegimenLineRepository;
import org.lamisplus.modules.base.repository.RegimenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RegimenLineService {

    private final RegimenLineRepository regimenLineRepository;
    private final RegimenRepository regimenRepository;


    public RegimenLine save(RegimenLine regimenLine) {
        Optional<RegimenLine> RegimenLine1 = regimenLineRepository.findById(regimenLine.getId());
        if(RegimenLine1.isPresent())throw new RecordExistException(RegimenLine.class, "Id", regimenLine.getId() +"");
        return regimenLineRepository.save(regimenLine);
    }

    public RegimenLine update(Long id, RegimenLine regimenLine) {
        Optional<RegimenLine> RegimenLine1 = regimenLineRepository.findById(id);
        if(!RegimenLine1.isPresent())throw new EntityNotFoundException(RegimenLine.class, "Id", id +"");
        regimenLine.setId(id);
        return regimenLineRepository.save(regimenLine);
    }
    public RegimenLine getRegimenLine(Long id){
        Optional<RegimenLine> regimenLine = this.regimenLineRepository.findById(id);
        if (!regimenLine.isPresent())throw new EntityNotFoundException(RegimenLine.class, "Id", id +"");
        return regimenLine.get();
    }

    public List<RegimenLine> getAllRegimenLines() {
        return regimenLineRepository.findAll();
    }

    public List<Regimen> getRegimenByRegimenLineId(Long id){
        List<Regimen> regimenList = this.regimenRepository.findAllByRegimenLineId(id);
        return regimenList;
    }


    public Boolean delete(Long id, RegimenLine regimenLine) {
        return true;
    }
}
