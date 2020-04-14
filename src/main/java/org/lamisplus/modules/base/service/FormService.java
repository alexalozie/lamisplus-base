package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.FormDTO;
import org.lamisplus.modules.base.domain.entity.Form;
import org.lamisplus.modules.base.domain.mapper.FormMapper;
import org.lamisplus.modules.base.repository.FormRepository;
import org.lamisplus.modules.base.util.UuidGenerator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;
    private final FormMapper formMapper;

    public List<Form> getAllForms() {
        List<Form> forms = this.formRepository.findAll();
        return forms;
    }


    public FormDTO getFormByFormIdAndProgramCode(Long Id, String serviceCode) {
        Optional<Form> formOptional= this.formRepository.findByIdAndProgramCode(Id, serviceCode);
        if(!formOptional.isPresent()) throw new EntityNotFoundException(Form.class, "Program Code", serviceCode);

        FormDTO formDTO = formMapper.toForm(formOptional.get());
        log.info("FormDTO - " + formDTO);
        return formDTO;
    }

    public Form save(FormDTO formDTO) {
        formDTO.setCode(UuidGenerator.getUuid());
        Optional<Form> formOptional = formRepository.findByCode(formDTO.getCode());
        if (formOptional.isPresent())
            throw new RecordExistException(Form.class, "Code", formDTO.getCode());
        Form form = formMapper.toFormDTO(formDTO);
        return formRepository.save(form);
    }

    public Form getForm(Long id) {
        Optional<Form> formOptional = this.formRepository.findById(id);
        if (!formOptional.isPresent()) throw new EntityNotFoundException(Form.class, "Id", id + "");
        return formOptional.get();
    }

    public Form getFormsByFormCode(String formCode) {
        Optional<Form> formOptional = formRepository.findByCode(formCode);
        if (!formOptional.isPresent()) throw new EntityNotFoundException(Form.class, "Code", formCode);
        return formOptional.get();
    }

    public List getFormsByUsageStatus(Integer usageStatus) {
        List<Form> formList = formRepository.findAllByUsageCode(usageStatus);
        return formList;
    }

    public Form update(Long id, FormDTO formDTO) {
        Optional<Form> formOptional = formRepository.findById(id);
        if(!formOptional.isPresent())throw new EntityNotFoundException(Form.class, "Id", id +"");
        formDTO.setId(id);
        Form form = formMapper.toFormDTO(formDTO);
        return formRepository.save(form);
    }

    public Boolean delete(Long id, Form form) {
        return true;
    }

}
