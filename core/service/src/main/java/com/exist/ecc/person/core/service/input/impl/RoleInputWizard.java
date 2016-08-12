package com.exist.ecc.person.core.service.input.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.validation.Validations;

public class RoleInputWizard extends AbstractInputWizard<Role> {

  public RoleInputWizard(InputReader reader, 
    InputExceptionHandler exceptionHandler) {
    super(reader, exceptionHandler);
  }

  public void initializeData(Map<String, PropertyData> data) {
    data.put("name", new PropertyData(
      new InputService.Builder<String>(getReader(), getExceptionHandler())
      .message("name")
      .validation(Validations.get(Role.class, "name"))));
  }

}