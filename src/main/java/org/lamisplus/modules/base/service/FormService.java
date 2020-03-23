package org.lamisplus.modules.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.apierror.RecordExistException;
import org.lamisplus.modules.base.domain.dto.FormDTO;
import org.lamisplus.modules.base.domain.entities.Form;
import org.lamisplus.modules.base.domain.entities.Service;
import org.lamisplus.modules.base.domain.mapper.FormMapper;
import org.lamisplus.modules.base.repository.FormRepository;
import org.lamisplus.modules.base.repository.ServicesRepository;
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
    private final ServicesRepository servicesRepository;
    private final FormMapper formMapper;

    public List<FormDTO> getAllForm() {
        List<Form> forms = this.formRepository.findAll();

        List<FormDTO> formDTOList = new ArrayList();
        forms.forEach(oneForm -> {
            if(oneForm.getServiceName().equals("GENERAL_SERVICE")) return;

            FormDTO formDTO = formMapper.toForm(oneForm);
            formDTOList.add(formDTO);
        });
        return formDTOList;
    }

    public List<FormDTO> getFormByServiceCode(String serviceCode) {
        List<FormDTO> formDTOList = new ArrayList();
        List<Form> formList = this.formRepository.findByServiceName(serviceCode);
        if(formList.size() < 1 || formList == null) throw new EntityNotFoundException(Form.class, "Service Code", serviceCode);

        formList.forEach(oneForm -> {
            if(oneForm.getServiceName().equals("GENERAL_SERVICE")) return;

            FormDTO formDTO = formMapper.toForm(oneForm);
            formDTOList.add(formDTO);
        });
        return formDTOList;
    }


    public Form save(FormDTO formDTO) {
        Optional <Service> service = this.servicesRepository.findByServiceName(formDTO.getServiceName());
        if(!service.isPresent()) throw new EntityNotFoundException(Service.class, "Service Name", formDTO.getServiceName());

        Optional<Form> formOptional = this.formRepository.findByName(formDTO.getName());
        if(formOptional.isPresent()) throw new RecordExistException(Form.class, "Form Name", formDTO.getName());

        final Form form = this.formMapper.toFormDTO(formDTO);
        log.info("Form - " + form);
        return this.formRepository.save(form);
    }

    public FormDTO getFormByFormIdAndServiceCode(Long Id, String serviceCode) {
        Optional<Form> formOptional= this.formRepository.findByIdAndServiceName(Id, serviceCode);
        if(!formOptional.isPresent()) throw new EntityNotFoundException(Form.class, "Service Code", serviceCode);

        FormDTO formDTO = formMapper.toForm(formOptional.get());
        log.info("FormDTO - " + formDTO);
        return formDTO;
    }

}
