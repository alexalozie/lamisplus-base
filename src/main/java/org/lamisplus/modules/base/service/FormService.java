package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.dto.FormDTO;
import org.lamisplus.modules.base.domain.entities.Form;
import org.lamisplus.modules.base.domain.mapper.FormMapper;
import org.lamisplus.modules.base.repository.FormRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;
    private final FormMapper formMapper;

    public List<FormDTO> getAllForm() {
        List<Form> forms = this.formRepository.findAll();

        List<FormDTO> formDTOList = new ArrayList();
        forms.forEach(oneForm -> {
            Form form = new Form();

            if(oneForm.getServiceName().equals("GENERAL_SERVICE")) return;

            FormDTO formDTO = formMapper.toForm(oneForm);
            formDTOList.add(formDTO);
        });
        return formDTOList;
    }

}
