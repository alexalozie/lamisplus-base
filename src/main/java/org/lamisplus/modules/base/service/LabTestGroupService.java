package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entity.LabTest;
import org.lamisplus.modules.base.domain.entity.LabTestGroup;
import org.lamisplus.modules.base.domain.mapper.LabTestMapper;
import org.lamisplus.modules.base.repository.LabTestGroupRepository;
import org.lamisplus.modules.base.repository.LabTestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class LabTestGroupService {
    private final LabTestRepository labTestRepository;
    private final LabTestGroupRepository labTestGroupRepository;
    private final LabTestMapper labTestMapper;

    public List<LabTestGroup> getAllLabTestGroups() {
        return this.labTestGroupRepository.findAll();
    }

    public LabTestGroup save(LabTestGroup labTestGroup) {
        Optional<LabTestGroup> labTestGroupOptional = labTestGroupRepository.findById(labTestGroup.getId());
        if (labTestGroupOptional.isPresent())
            throw new RecordExistException(LabTestGroup.class, "Id", labTestGroup.getId() + "");
        return labTestGroupRepository.save(labTestGroup);
    }

    public LabTestGroup getLabTestGroup(Long id) {
        Optional<LabTestGroup> labTestGroupOptional = this.labTestGroupRepository.findById(id);
        if (!labTestGroupOptional.isPresent()) throw new EntityNotFoundException(LabTestGroup.class, "Id", id + "");
        return labTestGroupOptional.get();
    }

    public LabTestGroup update(Long id, LabTestGroup labTestGroup) {
        Optional<LabTestGroup> labTestGroupOptional = labTestGroupRepository.findById(id);
        if(!labTestGroupOptional.isPresent())throw new EntityNotFoundException(LabTestGroup.class, "Id", id +"");
        labTestGroup.setId(id);
        return labTestGroupRepository.save(labTestGroup);
    }

    public List<LabTest> getLabTestsByLabTestGroupId(Long id){
        List<LabTest> labTests = this.labTestRepository.findAllByLabTestGroupId(id);
        return labTests;
    }

    public Boolean delete(Long id, LabTestGroup labTestGroup) {
        return true;
    }

}
