package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.entity.LabTest;
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
public class LabTestService {
    private final LabTestRepository labTestRepository;
    private final LabTestGroupRepository labTestGroupRepository;

    //List of Lab Test
    public List<LabTest> getAllLabTests() {
        return this.labTestRepository.findAll();
    }

    public LabTest save(LabTest labTest) {
        Optional<LabTest> labTestOptional = labTestRepository.findById(labTest.getId());
        if (labTestOptional.isPresent())
            throw new RecordExistException(LabTest.class, "Id", labTest.getId() + "");
        return labTestRepository.save(labTest);
    }

    public LabTest getLabTest(Long id) {
        Optional<LabTest> labTestOptional = this.labTestRepository.findById(id);
        if (!labTestOptional.isPresent()) throw new EntityNotFoundException(LabTest.class, "Id", id + "");
        return labTestOptional.get();
    }

    public LabTest update(Long id, LabTest labTest) {
        Optional<LabTest> labTestOptional = labTestRepository.findById(id);
        if(!labTestOptional.isPresent())throw new EntityNotFoundException(LabTest.class, "Id", id +"");
        labTest.setId(id);
        return labTestRepository.save(labTest);
    }

    public Boolean delete(Long id, LabTest labTest) {
        return true;
    }

}
