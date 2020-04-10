package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.FormDTO;
import org.lamisplus.modules.base.domain.entity.Form;
import org.lamisplus.modules.base.domain.entity.Program;
import org.lamisplus.modules.base.domain.mapper.FormMapper;
import org.lamisplus.modules.base.repository.FormRepository;
import org.lamisplus.modules.base.repository.ProgramRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;
    private final ProgramRepository programRepository;
    private final FormMapper formMapper;

    public List<FormDTO> getAllForm() {
        List<Form> forms = this.formRepository.findAll();

        List<FormDTO> formDTOList = new ArrayList();
        forms.forEach(oneForm -> {
            if(oneForm.getProgramCode().equals("GENERAL_SERVICE")) return;

            FormDTO formDTO = formMapper.toForm(oneForm);
            formDTOList.add(formDTO);
        });
        return formDTOList;
    }

    public List<FormDTO> getFormByServiceCode(String serviceCode) {
        List<FormDTO> formDTOList = new ArrayList();
        List<Form> formList = this.formRepository.findByProgramCode(serviceCode);
        if(formList.size() < 1 || formList == null) throw new EntityNotFoundException(Form.class, "Program Code", serviceCode);

        formList.forEach(oneForm -> {
            if(oneForm.getProgramCode().equals("GENERAL_SERVICE")) return;

            FormDTO formDTO = formMapper.toForm(oneForm);
            formDTOList.add(formDTO);
        });
        return formDTOList;
    }


    public Form save(FormDTO formDTO) {
        Optional <Program> service = this.programRepository.findByCode(formDTO.getProgramCode());
        if(!service.isPresent()) throw new EntityNotFoundException(Program.class, "Program Name", formDTO.getProgramCode());

        Optional<Form> formOptional = this.formRepository.findByCode(formDTO.getName());
        if(formOptional.isPresent()) throw new RecordExistException(Form.class, "Form Name", formDTO.getName());

        final Form form = this.formMapper.toFormDTO(formDTO);
        log.info("Form - " + form);
        return this.formRepository.save(form);
    }

    public FormDTO getFormByFormIdAndProgramCode(Long Id, String serviceCode) {
        Optional<Form> formOptional= this.formRepository.findByIdAndProgramCode(Id, serviceCode);
        if(!formOptional.isPresent()) throw new EntityNotFoundException(Form.class, "Program Code", serviceCode);

        FormDTO formDTO = formMapper.toForm(formOptional.get());
        log.info("FormDTO - " + formDTO);
        return formDTO;
    }

}
