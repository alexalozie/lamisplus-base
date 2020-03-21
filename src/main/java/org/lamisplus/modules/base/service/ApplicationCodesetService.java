package org.lamisplus.modules.base.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.domain.entities.ApplicationCodeset;
import org.lamisplus.modules.base.repository.ApplicationCodesetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ApplicationCodesetService {

    private final ApplicationCodesetRepository appCodesetRepo;

    public List<ApplicationCodeset> getAllByCodesetGroup(String codesetGroup){
        List<ApplicationCodeset> applicationCodesetList = appCodesetRepo.findBycodesetGroup(codesetGroup);
        if(applicationCodesetList.size() < 1)
            throw new EntityNotFoundException(ApplicationCodeset.class,"Codeset Group:", codesetGroup);
        return applicationCodesetList;
    }

    public List<ApplicationCodeset> getAllApplicationCodeset(){
        return appCodesetRepo.findAll();
    }
}
