package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entity.Organisation;
import org.lamisplus.modules.base.repository.OrganisationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrganisationService {

    private final OrganisationRepository organisationRepository;


    public Organisation save(Organisation organisation) {
        Optional<Organisation> organizationOptional = organisationRepository.findById(organisation.getId());
        if(organizationOptional.isPresent())throw new RecordExistException(Organisation.class, "Id", organisation.getId() +"");
        return organisationRepository.save(organisation);
    }

    public Organisation update(Long id, Organisation organisation) {
        Optional<Organisation> organizationOptional = organisationRepository.findById(id);
        if(!organizationOptional.isPresent())throw new EntityNotFoundException(Organisation.class, "Id", id +"");
        organisation.setId(id);
        return organisationRepository.save(organisation);
    }
    public Organisation getOrganization(Long id){
        Optional<Organisation> organizationOptional = this.organisationRepository.findById(id);
        if (!organizationOptional.isPresent())throw new EntityNotFoundException(Organisation.class, "Id", id +"");
        return organizationOptional.get();
    }

    public List<Organisation> getAllOrganizations() {
        return organisationRepository.findAll();
    }


    public Boolean delete(Long id, Organisation organisation) {
        return true;
    }
}
