package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entity.FormData;
import org.lamisplus.modules.base.domain.entity.FormData;
import org.lamisplus.modules.base.repository.FormDataRepository;
import org.lamisplus.modules.base.repository.FormDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FormDataService {

    private final FormDataRepository formDataRepository;

    public FormData save(FormData organization) {
        Optional<FormData> organizationOptional = formDataRepository.findById(organization.getId());
        if(organizationOptional.isPresent())throw new RecordExistException(FormData.class, "Id", organization.getId() +"");
        return formDataRepository.save(organization);
    }

    public FormData update(Long id, FormData organization) {
        Optional<FormData> organizationOptional = formDataRepository.findById(id);
        if(!organizationOptional.isPresent())throw new EntityNotFoundException(FormData.class, "Id", id +"");
        organization.setId(id);
        return formDataRepository.save(organization);
    }
    public FormData getFormData(Long id){
        Optional<FormData> organizationOptional = this.formDataRepository.findById(id);
        if (!organizationOptional.isPresent())throw new EntityNotFoundException(FormData.class, "Id", id +"");
        return organizationOptional.get();
    }

    public List<FormData> getAllFormData() {
        return formDataRepository.findAll();
    }


    public Boolean delete(Long id, FormData organization) {
        return true;
    }
}
