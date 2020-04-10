package org.lamisplus.modules.base.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.ApplicationCodesetDTO;
import org.lamisplus.modules.base.domain.entity.ApplicationCodeset;
import org.lamisplus.modules.base.domain.mapper.ApplicationCodesetMapper;
import org.lamisplus.modules.base.repository.ApplicationCodesetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ApplicationCodesetService {

    private final ApplicationCodesetRepository appCodesetRepo;
    private final ApplicationCodesetMapper applicationCodesetMapper;


    public List<ApplicationCodeset> getAllApplicationCodeset(){
        return appCodesetRepo.findAll();
    }

    public ApplicationCodeset save(ApplicationCodesetDTO applicationCodesetDTO){
        Optional<ApplicationCodeset> applicationCodesetOptional = appCodesetRepo.findByDisplayAndCodesetGroup(applicationCodesetDTO.getDisplay(), applicationCodesetDTO.getCodesetGroup());
        if (applicationCodesetOptional.isPresent()) throw new RecordExistException(ApplicationCodeset.class,"Display:",applicationCodesetDTO.getDisplay());
        final ApplicationCodeset applicationCodeset = applicationCodesetMapper.toApplicationCodeset(applicationCodesetDTO);
        log.info("applicationCodeset save successfully");
        return appCodesetRepo.save(applicationCodeset);
    }

    public List<ApplicationCodeset> getApplicationCodeByCodesetGroup(String codeSetGroup){
        List<ApplicationCodeset> applicationCodesetList = appCodesetRepo.findAllByCodesetGroupOrderByIdAsc(codeSetGroup);
        List<ApplicationCodesetDTO> applicationCodesetDTOList = new ArrayList<>();
        applicationCodesetList.forEach(applicationCodeset -> {
            final ApplicationCodesetDTO applicationCodesetDTO = applicationCodesetMapper.toApplicationCodesetDTO(applicationCodeset);
            applicationCodesetDTOList.add(applicationCodesetDTO);
        });
        return applicationCodesetList;
    }

    public ApplicationCodesetDTO getApplicationCodeset(Long id){
        Optional<ApplicationCodeset> applicationCodeset = appCodesetRepo.findById(id);
        if(!applicationCodeset.isPresent()) throw new EntityNotFoundException(ApplicationCodeset.class,"Display:",id+"");
        final ApplicationCodesetDTO applicationCodesetDTO = applicationCodesetMapper.toApplicationCodesetDTO(applicationCodeset.get());

        return  applicationCodesetDTO;
    }

    public ApplicationCodeset update(Long id, ApplicationCodesetDTO applicationCodesetDTO){
        Optional<ApplicationCodeset> applicationCodesetOptional = appCodesetRepo.findById(id);
        if(!applicationCodesetOptional.isPresent()) throw new EntityNotFoundException(ApplicationCodeset.class,"Display:",id+"");
        final ApplicationCodeset applicationCodeset = applicationCodesetMapper.toApplicationCodeset(applicationCodesetDTO);
        applicationCodeset.setId(id);
        log.info("applicationCodeset updated successfully");
        return appCodesetRepo.save(applicationCodeset);
    }

    public Integer delete(Long id){
        Optional<ApplicationCodeset> applicationCodesetOptional = appCodesetRepo.findById(id);
        if(!applicationCodesetOptional.isPresent()) throw new EntityNotFoundException(ApplicationCodeset.class,"Display:",id+"");
        applicationCodesetOptional.get().setArchived(1);
        return applicationCodesetOptional.get().getArchived();
    }

}
