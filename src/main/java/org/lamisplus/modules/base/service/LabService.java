package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.LabTestDTO;
import org.lamisplus.modules.base.domain.entities.LabTest;
import org.lamisplus.modules.base.domain.entities.LabTestGroup;
import org.lamisplus.modules.base.domain.mapper.LabTestMapper;
import org.lamisplus.modules.base.repository.LabTestGroupRepository;
import org.lamisplus.modules.base.repository.LabTestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class LabService {
    private final LabTestRepository labTestRepository;
    private final LabTestGroupRepository labTestGroupRepository;
    private final LabTestMapper labTestMapper;

    private static Object exist(Class o, String param1, String param2) {
        throw new RecordExistException(o,param1, param2);
    }

    private static LabTest notExit(Class o, String param1, String param2) {
        throw new EntityNotFoundException(o,param1, param2);
    }

    public List<LabTestGroup> getAllLabTestGroup() {
        return this.labTestGroupRepository.findAll();
    }

    //List of Lab Test
    public List<LabTestDTO> getAllLabTest(Long labTestCategoryId) {
        List<LabTestDTO> labTestDTOS = new ArrayList();
        List<LabTest> labTests = labTestRepository.findAllByLabTestCategoryId(labTestCategoryId);

        if(labTests.size() < 1 || labTests == null) notExit(LabTest.class, "Lab Test Category Id", labTestCategoryId + "");

        labTests.forEach(singleTest -> {
            LabTestDTO labTestDTO = labTestMapper.toLabTest(singleTest);

            labTestDTOS.add(labTestDTO);
            log.info("LabTest Description is " + singleTest.getDescription());
        });
        return labTestDTOS;
    }
}
