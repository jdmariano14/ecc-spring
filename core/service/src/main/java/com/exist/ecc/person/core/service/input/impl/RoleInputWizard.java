package com.exist.ecc.person.core.service.input.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.impl.RepeatReadHandler;
import com.exist.ecc.person.core.service.validation.Validations;

public class RoleInputWizard extends AbstractInputWizard<Role> {

  public RoleInputWizard(InputReader reader, 
    InputExceptionHandler handler) {
    super(reader, handler);
  }

  public void initializeData(Map<String, PropertyData> data) {
    String[] requiredStrings = {"name"};

    for (String requiredString : requiredStrings) {
      data.put(requiredString, new PropertyData(
        new InputService.Builder<String>(getReader(), new RepeatReadHandler())
        .message(requiredString)
        .validation(Validations.get(Role.class, requiredString))));  
    }
    
  }

}