package com.exist.ecc.person.core.service.input.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExtractor;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.validation.Validations;

public class RoleInputWizard extends AbstractInputWizard<Role> {

  public RoleInputWizard(InputExtractor extractor, 
    InputExceptionHandler exceptionHandler) {
    super(extractor, exceptionHandler);
  }

  public void initializeData(Map<String, InputService.Builder> data) {
    data.put("name", 
      new InputService.Builder<String>(getExtractor(), getExceptionHandler())
      .message("name")
      .validation(Validations.get(Role.class, "name")));
  }

}